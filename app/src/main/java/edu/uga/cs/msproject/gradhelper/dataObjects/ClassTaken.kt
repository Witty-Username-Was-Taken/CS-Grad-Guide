package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This Class is used as an Entity for the database. It represents a class that has been listed by
 * the user as having been taken. These objects are inserted into a table named Classes_Taken within
 * the database.
 *
 * @author  Tripp Guinn
 */
@Entity(tableName = "classes_taken")
class ClassTaken(
    @PrimaryKey val course_id : String,
    val className : String,
    val courseRequirement : String) {
}