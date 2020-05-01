package edu.uga.cs.msproject.gradhelper.locationFinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.Professor

/**
 * RecyclerView for handling the full list of professors in program
 *
 * @param       clickListener   Used to handle 'click' events
 *
 * @property    professors      List of Professors objects used to populate ViewHolders
 *
 * @author      Tripp Guinn
 */
class DirectoryListRecyclerViewAdapter( val clickListener: DirectorySelectionRecyclerViewClickListener) :
    RecyclerView.Adapter<DirectoryListViewHolder>() {

    var professors = emptyList<Professor>()

    interface DirectorySelectionRecyclerViewClickListener {
        fun listItemClicked(professor: Professor)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.faculty_view_holder, parent, false)
        return DirectoryListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return professors.size
    }

    override fun onBindViewHolder(holder: DirectoryListViewHolder, position: Int) {
        val resId = holder.itemView.resources.getIdentifier(professors.get(position).image,
            "drawable", "edu.uga.cs.msproject.gradhelper")

        holder.facultyName.text = professors.get(position).fname + " " + professors.get(position).lname
        holder.facultyTitle.text = professors.get(position).title
        holder.facultyImage.setImageResource(resId)
        holder.itemView.setOnClickListener{
            clickListener.listItemClicked(professors.get(position))
        }
    }

    internal fun setProfessors(professor : List<Professor>) {
        this.professors = professor
        notifyDataSetChanged()
    }
}