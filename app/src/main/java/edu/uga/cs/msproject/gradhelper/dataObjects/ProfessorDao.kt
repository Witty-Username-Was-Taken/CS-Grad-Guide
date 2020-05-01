package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Data Access Object for anything relating to Professors
 * @author  Tripp Guinn
 */
@Dao
interface ProfessorDao {

    /**
     * Query function returns Observable list of all professor objects in database, sorted in
     * Ascending order by last name
     * @return Observable list of all professor objects
     */
    @Query("Select * From professors Order by last_name ASC")
    fun getProfessors() : LiveData<List<Professor>>

    /**
     * Insert function used to add a Professor object to the Professors table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(professor: Professor)

    /**
     * Insert function used to add ProfessorResearchCrossRef object to Professors_Research table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertResearchProf(researchProf: ProfessorResearchCrossRef)

    /**
     * Delete function for erasing all contents of Professors table
     */
    @Query("DELETE FROM professors")
    suspend fun deleteAll()

    /**
     * Query function returns Observable list of ResearchWithProfessors objects in database.
     * These are objects containing one Research object and a list of Professors that are listed
     * as having interests in that Research.
     * @return Observable list of all ResearchWithProfessors objects
     */
    @Transaction
    @Query("SELECT * FROM research_topics Order by name ASC")
    fun getResearchWithProfessors(): LiveData<List<ResearchWithProfessors>>

    /**
     * Delete function for erasing all contents of Professors_Research table
     */
    @Query("DELETE FROM professors_research")
    suspend fun deleteAllProfResearch()

    /**
     * Query function that returns count of rows in Professors table.
     * Used to determine if table has been populated yet.
     * @return  Count of rows in Professors table
     */
    @Query("SELECT COUNT(id) FROM professors")
    suspend fun professorsCount(): Int

    /**
     * Query function that returns count of rows in Professors_Research table.
     * Used to determine if table has been populated yet.
     * @return  Count of rows in Professors_Research table
     */
    @Query("SELECT COUNT(id) FROM professors_research")
    suspend fun profResearchCount(): Int
}