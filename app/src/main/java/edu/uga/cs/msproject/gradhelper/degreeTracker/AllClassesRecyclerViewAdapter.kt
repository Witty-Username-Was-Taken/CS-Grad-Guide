package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassItem
import edu.uga.cs.msproject.gradhelper.dataObjects.EditInfoViewModel

/**
 * RecyclerView for handling the full list of classes not taken yet
 *
 * @param       editInfoViewModel   View Model used to inform data displayed in EditInfoFragment.
 *
 * @property    allClasses          List of ClassItems that have not been taken by the user.
 *                                  Used to populate ViewHolders.
 * @property    viewModel           Local reference to editInfoViewModel
 */
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
        holder.className.text = "CSCI" + allClasses.get(position).course_id + ": " +
                allClasses.get(position).course_name
        holder.addButton.setOnClickListener{
            viewModel.addNewClassTaken(allClasses.get(position))
        }
    }

    internal fun setAllClasses(allClasses: List<ClassItem>) {
        this.allClasses = allClasses
        notifyDataSetChanged()
    }
}