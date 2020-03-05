package edu.uga.cs.msproject.gradhelper.dataObjects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

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