package edu.uga.cs.msproject.gradhelper.researchTopics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.Professor

/**
 * RecyclerView for handling list of professors in ResearchDetailFragment
 *
 * @param       clickListener   Used to handle 'click' events
 *
 * @property    facultyList     List of Professor objects who have an interest in the Research topic
 *                              displayed on screen
 *
 * @author      Tripp Guinn
 */
class ResearchFacultyRecyclerViewAdapter(val clickListener: FacultySelectionRecyclerViewClickListener) :
    RecyclerView.Adapter<ResearchFacultyListViewHolder>() {

    var facultyList = emptyList<Professor>()

    interface FacultySelectionRecyclerViewClickListener {
        fun onListItemClicked(professor: Professor)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResearchFacultyListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.research_faculty_view_holder, parent, false)
        return ResearchFacultyListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return facultyList.size
    }

    override fun onBindViewHolder(holder: ResearchFacultyListViewHolder, position: Int) {
        val prof = facultyList.get(position)

        val resId = holder.itemView.resources.getIdentifier(facultyList.get(position).image,
            "drawable", "edu.uga.cs.msproject.gradhelper")

        holder.profImage.setImageResource(resId)
        holder.profName.text = prof.fname + " " + prof.lname
        holder.profTitle.text = prof.title

        holder.itemView.setOnClickListener{
            clickListener.onListItemClicked(facultyList.get(position))
        }
    }

    fun setResearchFacultyList(professors: List<Professor>) {
        this.facultyList = professors
        notifyDataSetChanged()
    }
}