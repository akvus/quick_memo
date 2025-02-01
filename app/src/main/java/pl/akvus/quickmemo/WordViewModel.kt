import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import pl.akvus.quickmemo.WordsApplication
import pl.akvus.quickmemo.entity.WordEntity

class WordViewModel(private val wordDao: WordDao) : ViewModel() {

    val unlearnedWords = liveData {
        emit(wordDao.getUnlearnedWords())
    }

    val allWords = liveData {
        emit(wordDao.getAllWords())
    }

    fun insertWord(wordA: String, wordB: String) {
        viewModelScope.launch {
            wordDao.insert(WordEntity(wordA = wordA, wordB = wordB))
        }
    }

    fun updateWord(word: WordEntity) {
        viewModelScope.launch {
            wordDao.updateWord(word)
        }
    }

    fun deleteWord(word: WordEntity) {
        viewModelScope.launch {
            wordDao.deleteWord(word)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])

                return WordViewModel(
                    (application as WordsApplication).database.wordDao
                ) as T
            }
        }
    }
}