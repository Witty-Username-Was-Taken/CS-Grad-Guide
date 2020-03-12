package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R

class EditInfoAllClassesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val className = itemView.findViewById<TextView>(R.id.class_name)
    val addButton = itemView.findViewById<ImageView>(R.id.addButton)
}