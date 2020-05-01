package edu.uga.cs.msproject.gradhelper.classList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassItem

/**
 * RecyclerView for handling the full list of classes offered
 *
 * @param       clickListener   Used to handle 'click' events
 *
 * @property    classList       List of ClassItems used to populate ViewHolders
 */
class ClassListRecyclerViewAdapter(val clickListener : ClassSelectionRecyclerViewClickListener) : RecyclerView.Adapter<ClassItemViewHolder>() {

    var classList = emptyList<ClassItem>()

    interface ClassSelectionRecyclerViewClickListener {
        fun classListItemClicked(classItem: ClassItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_view_holder, parent, false)
        return ClassItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return classList.size
    }

    override fun onBindViewHolder(holder: ClassItemViewHolder, position: Int) {

        // Use classList and position of view holder to set UI text from ClassItem
        holder.courseId.text = ("CSCI " + classList.get(position).course_id)
        holder.className.text = classList.get(position).course_name
        holder.semestersOffered.text = classList.get(position).semesterOffered

        // Set OnClickListener to use clickListener objects method and pass in ClassItem at this
        // position
        holder.itemView.setOnClickListener {
            clickListener.classListItemClicked(classList.get(position))
        }
    }

    /**
     * Set classList to passed in List of ClassItems.
     *
     * @param   classes List of ClassItems objects to set classList to .
     */

    fun setClasses(classes : List<ClassItem>) {
        classList = classes
        notifyDataSetChanged()  // updates UI
    }
}