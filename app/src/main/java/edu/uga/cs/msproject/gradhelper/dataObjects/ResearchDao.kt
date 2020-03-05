package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ResearchDao {

    @Query("Select * From research_topics Order by name ASC")
    fun getResearchTopics() : LiveData<List<Research>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(research: Research)

    @Query("DELETE FROM research_topics")
    suspend fun deleteAll()

    @Query("SELECT * FROM research_topics WHERE research_id = :id")
    suspend fun getResearchWithID(id: String) : Research
}