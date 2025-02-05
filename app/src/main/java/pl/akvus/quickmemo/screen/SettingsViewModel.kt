package pl.akvus.quickmemo.screen

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import pl.akvus.quickmemo.WordsApplication
import pl.akvus.quickmemo.db.WordDao
import pl.akvus.quickmemo.db.WordEntity

const val DEFAULT_REVEAL_TIME = 5
const val DEFAULT_SHOW_COUNTER = true
const val DEFAULT_REVERSE_WORDS = false

class SettingsViewModel(
    private val application: Application,
    private val sharedPreferences: SharedPreferences,
    private val wordDao: WordDao
) :
    AndroidViewModel(application) {
    private val _revealTime = MutableLiveData<Int>()
    val revealTime: LiveData<Int> get() = _revealTime
    private val _showCounter = MutableLiveData<Boolean>()
    val showCounter: LiveData<Boolean> get() = _showCounter
    private val _reverseWords = MutableLiveData<Boolean>()
    val reverseWords: LiveData<Boolean> get() = _reverseWords

    init {
        _revealTime.value = sharedPreferences.getInt(REVEAL_TIME_KEY, DEFAULT_REVEAL_TIME)
        _showCounter.value = sharedPreferences.getBoolean(SHOW_COUNTER_KEY, DEFAULT_SHOW_COUNTER)
        _reverseWords.value = sharedPreferences.getBoolean(REVERSE_WORDS_KEY, DEFAULT_REVERSE_WORDS)
    }

    fun setRevealTime(time: Int) {
        _revealTime.value = time

        with(sharedPreferences.edit()) {
            putInt(REVEAL_TIME_KEY, time)
            apply()
        }
    }

    fun setShowCounter(show: Boolean) {
        _showCounter.value = show

        with(sharedPreferences.edit()) {
            putBoolean(SHOW_COUNTER_KEY, show)
            apply()
        }
    }

    fun setReverseWords(reverse: Boolean) {
        _reverseWords.value = reverse

        with(sharedPreferences.edit()) {
            putBoolean(REVERSE_WORDS_KEY, reverse)
            apply()
        }
    }

    fun exportData() {
        try {
            val words = wordDao.getAllWords().value ?: return
            val jsonWords = Gson().toJson(words)

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, jsonWords)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            application.startActivity(shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

        } catch (e: Exception) {
            Log.e("GsonError", "Error " + e.message.toString())
        }
    }

    fun importData() {
        // TODO
        val gson = Gson()
        val jsonString = "[{\"wordA\":\"hello\",\"wordB\":\"world\"}, ...]"
        val wordList: List<WordEntity> =
            gson.fromJson(jsonString, object : TypeToken<List<WordEntity>>() {}.type)

        viewModelScope.launch {
            wordDao.insertAll(wordList)
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "settings"

        private const val SHOW_COUNTER_KEY = "show_counter"
        private const val REVERSE_WORDS_KEY = "reverse_words"
        private const val REVEAL_TIME_KEY = "reveal_time"


        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val sharedPreferences: SharedPreferences =
                    application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

                return SettingsViewModel(
                    application,
                    sharedPreferences,
                    (application as WordsApplication).database.wordDao
                ) as T
            }
        }
    }
}
