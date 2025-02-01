package pl.akvus.quickmemo

import WordDatabase
import android.app.Application

class WordsApplication : Application() {
    val database by lazy { WordDatabase.getInstance(this) }
}