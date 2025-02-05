package pl.akvus.quickmemo.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import pl.akvus.quickmemo.WordsApplication
import pl.akvus.quickmemo.db.WordDao
import pl.akvus.quickmemo.db.WordEntity

class WordViewModel(private val wordDao: WordDao) : ViewModel() {

    val unlearnedWords = liveData {
        emitSource(wordDao.getUnlearnedWords())
    }

    val allWords = liveData {
        emitSource(wordDao.getAllWords())
    }

    fun insertWord(wordA: String, wordB: String, color: Int) {
        viewModelScope.launch {
            wordDao.insert(WordEntity(wordA = wordA.trim(), wordB = wordB.trim(), color = color))
        }
    }

    suspend fun updateWord(word: WordEntity) {
        wordDao.updateWord(
            word.copy(
                wordA = word.wordA.trim(),
                wordB = word.wordB.trim()
            )
        )
    }

    suspend fun reverseLearnt(word: WordEntity) {
        updateWord(
            word.copy(
                isLearned = !word.isLearned,
                learntDate = if (word.isLearned) null else System.currentTimeMillis()
            )
        )
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
