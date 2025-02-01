package pl.akvus.quickmemo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val wordA: String,
    val wordB: String,
    val isLearned: Boolean = false
)