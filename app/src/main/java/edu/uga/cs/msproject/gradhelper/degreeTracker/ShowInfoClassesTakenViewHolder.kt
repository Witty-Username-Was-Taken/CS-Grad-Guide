package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R

/**
 * Simple ViewHolder subclass for ShowInfoClassesTakenRecyclerViewAdapter
 *
 * @author  Tripp Guinn
 */
class ShowInfoClassesTakenViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){
    val courseName = itemView.findViewById<TextView>(R.id.class_id)
}