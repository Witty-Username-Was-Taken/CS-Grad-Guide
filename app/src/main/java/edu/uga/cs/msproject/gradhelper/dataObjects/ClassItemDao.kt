package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ClassItemDao {

    @Query("Select * From classes Order By course_id ASC")
    fun getClasses() : LiveData<List<ClassItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClass(classItem: ClassItem)

    @Query("Delete From classes")
    suspend fun deleteClasses()
}