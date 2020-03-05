package edu.uga.cs.msproject.gradhelper.dataObjects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professors")
data class Professor(@PrimaryKey
                     val id : String,
                     @ColumnInfo(name = "last_name") val lname : String,
                     @ColumnInfo(name = "first_name") val fname : String,
                     val title : String,
                     @ColumnInfo(name = "email_address") val email : String,
                     @ColumnInfo(name = "phone_number") val phone : String?,
                     @ColumnInfo(name = "office_number") val office : String?,
                     val building : String?,
                     val website : String?,
                     val image : String,
                     @ColumnInfo(name = "research_focus") val research : String?) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(lname)
        dest.writeString(fname)
        dest.writeString(title)
        dest.writeString(email)
        dest.writeString(phone)
        dest.writeString(office)
        dest.writeString(building)
        dest.writeString(website)
        dest.writeString(image)
        dest.writeString(research)
    }

    companion object CREATOR: Parcelable.Creator<Professor> {

        override fun createFromParcel(source: Parcel): Professor = Professor(source)

        override fun newArray(size: Int): Array<Professor?> = arrayOfNulls(size)
    }
}