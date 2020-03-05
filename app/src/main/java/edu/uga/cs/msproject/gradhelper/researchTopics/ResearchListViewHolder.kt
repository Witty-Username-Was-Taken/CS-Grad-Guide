package edu.uga.cs.msproject.gradhelper.researchTopics

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R

class ResearchListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val researchName = itemView.findViewById<TextView>(R.id.class_name)
}