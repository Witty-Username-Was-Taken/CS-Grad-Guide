package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassTaken

/**
 * RecyclerView for handling the list of classes taken by the user.
 *
 * @property    classesTaken        List of ClassItems that have been taken by the user.
 *                                  Used to populate ViewHolders.
 *
 * @author      Tripp Guinn
 */
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
        holder.courseName.text = "CSCI" + classesTaken.get(position).course_id + ": " +
                classesTaken.get(position).className
    }

    internal fun setClassesTaken(classesTaken: List<ClassTaken>) {
        this.classesTaken = classesTaken
        notifyDataSetChanged()
    }
}