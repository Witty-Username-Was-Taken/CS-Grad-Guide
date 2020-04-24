package edu.uga.cs.msproject.gradhelper.dataObjects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

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