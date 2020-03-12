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
     */
    @Query("Select * From classes Order By course_id ASC")
    fun getClasses() : LiveData<List<ClassItem>>

    @Query("SELECT * FROM classes_taken ORDER BY course_id ASC")
    fun getClassesTaken() : LiveData<List<ClassTaken>>

    @Query("SELECT * FROM classes WHERE course_id = :id")
    fun getClass(id : String) : LiveData<ClassItem>

    @Query("SELECT classes.* FROM classes LEFT JOIN classes_taken ON classes.course_id = classes_taken.course_id WHERE classes_taken.course_id IS NULL")
    fun getClassesNotTaken() : LiveData<List<ClassItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClass(classItem: ClassItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClassTaken(classTaken: ClassTaken)

    @Query("Delete From classes")
    suspend fun deleteClasses()

    @Query("SELECT COUNT(course_id) FROM classes")
    suspend fun classesCount(): Int

    @Query("SELECT COUNT(course_id) FROM classes_taken")
    suspend fun classesTakenCount(): Int

    @Delete
    suspend fun deleteClassTaken(classTaken: ClassTaken)
}