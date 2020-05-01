package edu.uga.cs.msproject.gradhelper.locationFinder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R

/**
 * ViewHolder subclass for LocationListRecyclerViewAdapter
 *
 * @author  Tripp Guinn
 */
class LocationListViewHolder(itemView: View) :
RecyclerView.ViewHolder(itemView) {

    val locationName = itemView.findViewById<TextView>(R.id.locationName)
}