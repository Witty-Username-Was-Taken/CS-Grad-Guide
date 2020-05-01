package edu.uga.cs.msproject.gradhelper.researchTopics

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R

/**
 * ViewHolder subclass for ResearchFacultyRecyclerViewAdapter
 *
 * @author  Tripp Guinn
 */
class ResearchFacultyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val profImage = itemView.findViewById<ImageView>(R.id.researchProfImage)
    val profName = itemView.findViewById<TextView>(R.id.researchProfName)
    val profTitle = itemView.findViewById<TextView>(R.id.researchProfTitle)
}