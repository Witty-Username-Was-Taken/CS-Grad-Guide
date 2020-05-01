package edu.uga.cs.msproject.gradhelper.dataObjects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This Class is used as an Entity for the database. It represents research topics that are listed
 * on the college's website and are created from research_list.csv. These objects are inserted into
 * a table named Research_Topics within the database. It is made Parcelable so that an object of
 * this Class can be passed from Fragment to Fragment to update the UI of a screen.
 *
 * @param   research_id             Unique Int used as Primary Key
 * @param   name                    Name of research topic
 * @param   description             Topic description pulled from college website
 *
 * @author  Tripp Guinn
 */
@Entity(tableName = "research_topics")
data class Research(
    @PrimaryKey val research_id : String,
    val name : String,
    val description : String) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(research_id)
        dest.writeString(name)
        dest.writeString(description)
    }

    companion object CREATOR: Parcelable.Creator<Research> {

        override fun createFromParcel(source: Parcel): Research = Research(source)

        override fun newArray(size: Int): Array<Research?> = arrayOfNulls(size)
    }
}