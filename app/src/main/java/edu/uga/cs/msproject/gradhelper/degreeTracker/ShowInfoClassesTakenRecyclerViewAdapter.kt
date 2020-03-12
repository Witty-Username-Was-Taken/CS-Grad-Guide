package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassTaken

class ShowInfoClassesTakenRecyclerViewAdapter : RecyclerView.Adapter<ShowInfoClassesTakenViewHolder>() {

    var classesTaken = emptyList<ClassTaken>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowInfoClassesTakenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.show_info_classes_taken_view_holder,parent,false)
        return ShowInfoClassesTakenViewHolder(view)
    }

    override fun getItemCount(): Int {
        return classesTaken.count()
    }

    override fun onBindViewHolder(holder: ShowInfoClassesTakenViewHolder, position: Int) {
        holder.courseId.text = classesTaken.get(position).course_id
        holder.courseName.text = classesTaken.get(position).className
    }

    internal fun setClassesTaken(classesTaken: List<ClassTaken>) {
        this.classesTaken = classesTaken
        notifyDataSetChanged()
    }
}