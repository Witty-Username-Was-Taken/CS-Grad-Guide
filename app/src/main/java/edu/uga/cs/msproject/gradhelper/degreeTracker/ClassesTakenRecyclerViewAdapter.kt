package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassTaken
import edu.uga.cs.msproject.gradhelper.dataObjects.EditInfoViewModel

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
        holder.className.text = classesTaken.get(position).course_id

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