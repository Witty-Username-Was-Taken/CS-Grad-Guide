package edu.uga.cs.msproject.gradhelper.locationFinder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R

class LocationListRecyclerViewAdapter(val locations : Array<String>,
                                      val clickListener: LocationSelectionRecyclerViewClickListener) :
    RecyclerView.Adapter<LocationListViewHolder>() {

    interface LocationSelectionRecyclerViewClickListener {
        fun listItemClicked(value: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_view_holder, parent, false)
        return LocationListViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return locations.count()
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, position: Int) {
        holder.locationName.text = locations[position]
        holder.itemView.setOnClickListener{
            clickListener.listItemClicked(locations[position])
        }
    }

    companion object {
        private const val TAG = "LOCATIONRECYCLERADAPTER"
    }
}