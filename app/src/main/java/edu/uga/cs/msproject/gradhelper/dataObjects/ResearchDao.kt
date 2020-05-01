package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for anything relating to Research
 * @author  Tripp Guinn
 */
@Dao
interface ResearchDao {

    /**
     * Query functions that returns an Observable list of Research objects from the Research_Topics
     * table sorted in Ascending order.
     * @return  Observable list of all Research objects in database
     */
    @Query("Select * From research_topics Order by name ASC")
    fun getResearchTopics() : LiveData<List<Research>>

    /**
     * Inserts Research object in Research_topics table.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(research: Research)

    /**
     * Deletes all contents from Research_Topics table.
     */
    @Query("DELETE FROM research_topics")
    suspend fun deleteAll()

    /**
     * Returns count of rows in Research_Topics table.
     * Used to determine if table has been populated.
     * @return  Count of rows in Research_Topics table
     */
    @Query("SELECT COUNT(research_id) FROM research_topics")
    suspend fun researchCount(): Int
}