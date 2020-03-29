package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classes")
data class ClassItem(
    @PrimaryKey val course_id : String,
    val course_name : String,
    val description : String,
    val semesterOffered : String,
    val requirementSatisfied : String) {
}