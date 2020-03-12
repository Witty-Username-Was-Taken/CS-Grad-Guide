package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classes_taken")
class ClassTaken(
    @PrimaryKey val course_id : String,
    val className : String) {
}