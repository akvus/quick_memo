package pl.akvus.quickmemo.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: WordEntity)

    @Query("SELECT * FROM words WHERE isLearned = 0 ORDER BY RANDOM()")
    suspend fun getUnlearnedWords(): List<WordEntity>

    @Query("SELECT * FROM words")
    fun getAllWords(): LiveData<List<WordEntity>>

    @Update
    suspend fun updateWord(word: WordEntity)

    @Delete
    suspend fun deleteWord(word: WordEntity)
}