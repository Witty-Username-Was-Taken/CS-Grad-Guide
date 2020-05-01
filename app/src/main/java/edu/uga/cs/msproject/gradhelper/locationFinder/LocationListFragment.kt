package edu.uga.cs.msproject.gradhelper.locationFinder

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uga.cs.msproject.gradhelper.R

/**
 * Fragment used to display Location List screen.
 *
 * @author  Tripp Guinn
 */
class LocationListFragment : Fragment(),
LocationListRecyclerViewAdapter.LocationSelectionRecyclerViewClickListener {

    private var listener: OnLocationListListener? = null
    lateinit var locationsRecyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLocationListListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val locations = activity!!.resources.getStringArray(R.array.location_types)

        view?.let {
            locationsRecyclerView = it.findViewById(R.id.location_list_recyclerview)
            locationsRecyclerView.layoutManager = LinearLayoutManager(activity)
            locationsRecyclerView.adapter =
                LocationListRecyclerViewAdapter(
                    locations,
                    this
                )
        }
    }

    // Handles
    override fun listItemClicked(value: String) {
        listener?.onLocationItemClicked(value)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnLocationListListener {
        fun onLocationItemClicked(value: String)
    }
}
