package edu.uga.cs.msproject.gradhelper.dataObjects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This Class is used as an Entity for the database. It represents classes that are listed on the
 * college's website and are created from class_list.csv. These objects are inserted into a table
 * named Classes within the database. It is made Parcelable so that an object of this Class can be
 * passed from Fragment to Fragment to update the UI of a screen.
 *
 * @param   course_id               Unique Int used as Primary Key
 * @param   course_name             Name of course
 * @param   description             Course description pulled from college website
 * @param   semesterOffered         Semesters when course is offered
 * @param   requirementSatisfied    What requirement this course satisfies
 * @author  Tripp Guinn
 */
@Entity(tableName = "classes")
data class ClassItem(
    @PrimaryKey val course_id : String,
    val course_name : String,
    val description : String,
    val semesterOffered : String,
    val requirementSatisfied : String) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(course_id)
        dest.writeString(course_name)
        dest.writeString(description)
        dest.writeString(semesterOffered)
        dest.writeString(requirementSatisfied)
    }

    companion object CREATOR: Parcelable.Creator<ClassItem> {

        override fun createFromParcel(source: Parcel): ClassItem = ClassItem(source)

        override fun newArray(size: Int): Array<ClassItem?> = arrayOfNulls(size)
    }
}