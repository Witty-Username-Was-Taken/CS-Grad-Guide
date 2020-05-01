package edu.uga.cs.msproject.gradhelper.dataObjects

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This Class is used as an Entity for the database. It represents professors in the Computer
 * Science department and their information is pulled from professor_info.csv.These objects are
 * inserted into a table named Professors within the database. It is made Parcelable so that an
 * object of this Class can be passed from Fragment to Fragment to update the UI of a screen.
 *
 * @param   id          Unique Int used as Primary Key
 * @param   lname       Professor's last name
 * @param   fname       Professor's first name
 * @param   title       Professor's job title
 * @param   email       Professor's email address
 * @param   phone       Professor's office phone number
 * @param   office      Professor's office number
 * @param   building    The building the professor's office is located in
 * @param   website     Professor's academic website
 * @param   image       Name of image file within drawable folder
 * @author  Tripp Guinn
 */
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