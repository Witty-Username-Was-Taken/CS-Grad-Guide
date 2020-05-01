package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassTaken
import edu.uga.cs.msproject.gradhelper.dataObjects.EditInfoViewModel

/**
 * RecyclerView for handling the full list of classes not taken yet
 *
 * @param       editInfoViewModel   View Model used to inform data displayed in EditInfoFragment.
 *
 * @property    classesTaken        List of ClassItems that have been taken by the user.
 *                                  Used to populate ViewHolders.
 * @property    viewModel           Local reference to editInfoViewModel
 */
class ClassesTakenRecyclerViewAdapter(editInfoViewModel: EditInfoViewModel): RecyclerView.Adapter<EditInfoClassesTakenViewHolder>() {

    var classesTaken = emptyList<ClassTaken>()
    val viewModel = editInfoViewModel

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditInfoClassesTakenViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_taken_view_holder, parent, false)
        return EditInfoClassesTakenViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return classesTaken.count()
    }

    override fun onBindViewHolder(holder: EditInfoClassesTakenViewHolder, position: Int) {
        holder.className.text = "CSCI" + classesTaken.get(position).course_id + ": " +
                classesTaken.get(position).className

        holder.removeButton.setOnClickListener{
            viewModel.removeClass(classesTaken.get(position))
        }
    }

    internal fun setClassesTaken(classesTaken : List<ClassTaken>) {
        this.classesTaken = classesTaken
        System.out.println("ClassesTaken: " + this.classesTaken)
        notifyDataSetChanged()
    }
}