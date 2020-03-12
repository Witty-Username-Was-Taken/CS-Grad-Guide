package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import kotlinx.android.synthetic.main.add_class_view_holder.view.*

class EditInfoClassesTakenViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val className = itemView.findViewById<TextView>(R.id.class_name)
    val removeButton = itemView.findViewById<ImageView>(R.id.deleteButton)
}