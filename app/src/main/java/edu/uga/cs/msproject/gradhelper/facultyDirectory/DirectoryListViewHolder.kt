package edu.uga.cs.msproject.gradhelper.locationFinder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R

class DirectoryListViewHolder(itemView: View) :
RecyclerView.ViewHolder(itemView) {

    val facultyName = itemView.findViewById<TextView>(R.id.faculty_name)
    val facultyTitle = itemView.findViewById<TextView>(R.id.faculty_title)
    val facultyImage = itemView.findViewById<ImageView>(R.id.faculty_image)
}