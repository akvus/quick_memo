package pl.akvus.quickmemo.db

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
    fun getUnlearnedWords(): LiveData<List<WordEntity>>

    @Query("SELECT * FROM words ORDER BY wordA")
    fun getAllWords(): LiveData<List<WordEntity>>

    @Update
    suspend fun updateWord(word: WordEntity)

    @Delete
    suspend fun deleteWord(word: WordEntity)
}