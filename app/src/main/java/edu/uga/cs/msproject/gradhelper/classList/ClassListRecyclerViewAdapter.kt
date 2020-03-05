package edu.uga.cs.msproject.gradhelper.classList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassItem

class ClassListRecyclerViewAdapter : RecyclerView.Adapter<ClassItemViewHolder>() {

    var classList = emptyList<ClassItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_view_holder, parent, false)
        return ClassItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return classList.size
    }

    override fun onBindViewHolder(holder: ClassItemViewHolder, position: Int) {
        holder.courseId.text = ("CSCI " + classList.get(position).course_id)
        holder.className.text = classList.get(position).course_name
        holder.semestersOffered.text = classList.get(position).semesterOffered
    }

    fun setClasses(classes : List<ClassItem>) {
        classList = classes
        notifyDataSetChanged()
    }
}