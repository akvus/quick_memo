package pl.akvus.quickmemo.screen

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val _revealTime = MutableLiveData<Int>()
    val revealTime: LiveData<Int> get() = _revealTime
    private val _showCounter = MutableLiveData<Boolean>()
    val showCounter: LiveData<Boolean> get() = _showCounter
    private val _reverseWords = MutableLiveData<Boolean>()
    val reverseWords: LiveData<Boolean> get() = _reverseWords

    // TODO inject?
    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        _revealTime.value = sharedPreferences.getInt("reveal_time", 5)
        _showCounter.value = sharedPreferences.getBoolean("show_counter", true)
        _reverseWords.value = sharedPreferences.getBoolean("reverse_words", false)
    }

    fun setRevealTime(time: Int) {
        _revealTime.value = time

        with(sharedPreferences.edit()) {
            putInt("reveal_time", time)
            apply()
        }
    }

    fun setShowCounter(show: Boolean) {
        _showCounter.value = show

        with(sharedPreferences.edit()) {
            putBoolean("show_counter", show)
            apply()
        }
    }

    fun setReverseWords(reverse: Boolean) {
        _reverseWords.value = reverse

        with(sharedPreferences.edit()) {
            putBoolean("reverse_words", reverse)
            apply()
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

                return SettingsViewModel(
                    application
                ) as T
            }
        }
    }
}