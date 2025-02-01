import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import pl.akvus.quickmemo.entity.WordEntity

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: WordEntity)

    @Query("SELECT * FROM words WHERE isLearned = 0 ORDER BY RANDOM()")
    suspend fun getUnlearnedWords(): List<WordEntity>

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<WordEntity>

    @Update
    suspend fun updateWord(word: WordEntity)

    @Delete
    suspend fun deleteWord(word: WordEntity)
}