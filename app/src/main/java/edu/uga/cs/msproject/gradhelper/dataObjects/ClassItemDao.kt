package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Data Access Object designed for interacting with tables dealing with classes
 * @author tguinn
 */
@Dao
interface ClassItemDao {

    /**
     * Query function returns Observable list of all ClassItems  in 'classes' table sorted in
     * ascending order by course_id
     * @return  Observable list of all ClassItems
     */
    @Query("Select * From classes Order By course_id ASC")
    fun getClasses() : LiveData<List<ClassItem>>

    /**
     * Query function returns Observable list of all ClassTaken objects in 'classes_taken' table
     * sorted in ascending order by course_id
     * @return  Observable list of all ClassTaken objects
     */
    @Query("SELECT * FROM classes_taken ORDER BY course_id ASC")
    fun getClassesTaken() : LiveData<List<ClassTaken>>

    /**
     * Query function returns Observable list of all ClassItems in 'classes' table that do not also
     * exist in 'classes_taken' table (classes the user has NOT listed as taken)
     * @return  Observable list of ClassItems that have not been taken by the student
     */
    @Query("SELECT classes.* FROM classes LEFT JOIN classes_taken ON classes.course_id = classes_taken.course_id WHERE classes_taken.course_id IS NULL")
    fun getClassesNotTaken() : LiveData<List<ClassItem>>

    /**
     * Query function returns Observable list of 6000 level courses as ClassTaken objects
     * @return  Observable list of ClassTaken objects consisting of 6000 level courses
     */
    @Query("SELECT * FROM classes_taken WHERE courseRequirement IN ('6000 level', 'Theory', 'System Design', 'Software Design')")
    fun getSixThousandLevelTaken() : LiveData<List<ClassTaken>>

    /**
     * Query function returns Observable list of 8000 level courses as ClassTaken objects
     * @return  Observable list of ClassTaken objects consisting of 8000 level courses
     */
    @Query("SELECT * FROM classes_taken WHERE courseRequirement IN ('8000 level')")
    fun getEightThousandLevelTaken() : LiveData<List<ClassTaken>>

    /**
     * Query function returns Observable list of 8000 level courses as ClassTaken objects
     * @return  Observable list of ClassTaken objects consisting of 8000 level courses
     */
    @Query("SELECT * FROM classes_taken WHERE courseRequirement IN ('8000 Level', '6000 level', 'Theory', 'System Design', 'Software Design')")
    fun getAdvancedCourseworkTaken() : LiveData<List<ClassTaken>>

    /**
     * Query function returns Observable Int of count of Research Seminar courses.
     * Used to verify that student has taken the course as an additional degree requirement.
     * @return  Observable Int representing count of Research Seminar courses taken
     */
    @Query("SELECT COUNT(course_id) FROM classes_taken WHERE courseRequirement = 'Research Seminar'")
    fun getResearchSeminarTaken() : LiveData<Int>

    /**
     * Query function returns Observable Int of count of Masters Project courses.
     * Used to verify that student has taken the course as an additional degree requirement.
     * @return  Observable Int representing count of Masters Project courses taken
     */
    @Query("SELECT COUNT(course_id) FROM classes_taken WHERE courseRequirement = 'Masters Project'")
    fun getMastersProjectTaken() : LiveData<Int>

    /**
     * Query function returns Observable Int of count of Masters Research courses.
     * Used to verify that student has taken the course as an additional degree requirement.
     * @return  Observable Int representing count of Masters Research courses taken
     */
    @Query("SELECT COUNT(course_id) FROM classes_taken WHERE courseRequirement = 'Masters Research'")
    fun getMastersResearchTaken() : LiveData<Int>

    /**
     * Query function returns Observable Int of count of Masters Thesis courses.
     * Used to verify that student has taken the course as an additional degree requirement.
     * @return  Observable Int representing count of Masters Thesis courses taken
     */
    @Query("SELECT COUNT(course_id) FROM classes_taken WHERE courseRequirement = 'Masters Thesis'")
    fun getMastersThesisTaken() : LiveData<Int>

    /**
     * Query function returns Observable Int of count of Doctoral Dissertation courses.
     * Used to verify that student has taken the course as an additional degree requirement.
     * @return  Observable Int representing count of Doctoral Dissertation courses taken
     */
    @Query("SELECT COUNT(course_id) FROM classes_taken WHERE courseRequirement = 'Dissertation'")
    fun getDoctoralDissertationTaken() : LiveData<Int>

    /**
     * Insert function to add a new ClassItem to the 'classes' table
     * @param   classItem   ClassItem to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClass(classItem: ClassItem)

    /**
     * Insert function to add a new ClassTaken object to the 'classes_taken' table
     * @param   classTaken  ClassTaken object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClassTaken(classTaken: ClassTaken)

    /**
     * Query to delete everything from classes table
     */
    @Query("Delete From classes")
    suspend fun deleteClasses()

    /**
     * Query to get count of classes
     * @return  Count of classes
     */
    @Query("SELECT COUNT(course_id) FROM classes")
    suspend fun classesCount(): Int

    /**
     * Query to get count of classes taken
     * @return  Count of classes taken
     */
    @Query("SELECT COUNT(course_id) FROM classes_taken")
    suspend fun classesTakenCount(): Int

    /**
     * Query to delete a specific class taken
     */
    @Delete
    suspend fun deleteClassTaken(classTaken: ClassTaken)
}