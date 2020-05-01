package edu.uga.cs.msproject.gradhelper.classList

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import kotlinx.android.synthetic.main.class_view_holder.view.*

/**
 * ViewHolder class for items in ClassListRecyclerViewAdapter
 *
 * @author  Tripp Guinn
 */
class ClassItemViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView) {
    val className = itemView.findViewById<TextView>(R.id.class_name)
    val semestersOffered = itemView.findViewById<TextView>(R.id.semester_offered_content)
    val courseId = itemView.findViewById<TextView>(R.id.course_id)
}