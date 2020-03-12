package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassItem
import edu.uga.cs.msproject.gradhelper.dataObjects.EditInfoViewModel

class AllClassesRecyclerViewAdapter(editInfoViewModel: EditInfoViewModel) : RecyclerView.Adapter<EditInfoAllClassesViewHolder>() {

    var allClasses = emptyList<ClassItem>()
    var viewModel = editInfoViewModel

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditInfoAllClassesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_class_view_holder, parent, false)
        return EditInfoAllClassesViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return allClasses.count()
    }

    override fun onBindViewHolder(holder: EditInfoAllClassesViewHolder, position: Int) {
        holder.className.text = allClasses.get(position).course_name
        holder.addButton.setOnClickListener{
            viewModel.addNewClassTaken(allClasses.get(position))
        }
    }

    internal fun setAllClasses(allClasses: List<ClassItem>) {
        this.allClasses = allClasses
        notifyDataSetChanged()
    }
}