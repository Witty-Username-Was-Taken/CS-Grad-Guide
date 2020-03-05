package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProfessorDao {

    @Query("Select * From professors Order by last_name ASC")
    fun getProfessors() : LiveData<List<Professor>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(professor: Professor)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertResearchProf(researchProf: ProfessorResearchCrossRef)

    @Query("DELETE FROM professors")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM research_topics Order by name ASC")
    fun getResearchWithProfessors(): LiveData<List<ResearchWithProfessors>>

    @Query("DELETE FROM professors_research")
    suspend fun deleteAllProfResearch()

}