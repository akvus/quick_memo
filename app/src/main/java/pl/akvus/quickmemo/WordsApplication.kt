package pl.akvus.quickmemo

import android.app.Application
import pl.akvus.quickmemo.entity.WordDatabase

class WordsApplication : Application() {
    val database by lazy { WordDatabase.getInstance(this) }
}