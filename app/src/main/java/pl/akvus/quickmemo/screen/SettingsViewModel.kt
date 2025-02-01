package pl.akvus.quickmemo.screen

import android.app.Application
import android.content.Context
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

    init {
        val sharedPreferences = application.getSharedPreferences("settings", Context.MODE_PRIVATE)
        _revealTime.value = sharedPreferences.getInt("reveal_time", 5)
    }

    fun setRevealTime(time: Int) {
        _revealTime.value = time
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("reveal_time", time)
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