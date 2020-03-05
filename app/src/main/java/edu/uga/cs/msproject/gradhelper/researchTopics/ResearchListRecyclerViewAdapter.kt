package edu.uga.cs.msproject.gradhelper.researchTopics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.Research

class ResearchListRecyclerViewAdapter(val clickListener: ResearchSelectionRecyclerViewClickListener)
    : RecyclerView.Adapter<ResearchListViewHolder>() {

    var researchList = emptyList<Research>()

    interface ResearchSelectionRecyclerViewClickListener {
        fun listItemClicked(research: Research)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResearchListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.research_view_holder,parent,false)
        return ResearchListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return researchList.size
    }

    override fun onBindViewHolder(holder: ResearchListViewHolder, position: Int) {
        holder.researchName.text = researchList.get(position).name
        holder.itemView.setOnClickListener{
            clickListener.listItemClicked(researchList.get(position))
        }
    }

    fun setResearchTopics(research: List<Research>) {
        this.researchList = research
        notifyDataSetChanged()
    }
}