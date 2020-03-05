package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(@PrimaryKey val student_id : Int,
              val student_name : String,
              val degree_objective : String) {
}