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
import edu.uga.cs.msproject.gradhelper.dataObjects.Location
import edu.uga.cs.msproject.gradhelper.facultyDirectory.DirectoryListFragment
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LocationListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LocationListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationListFragment : Fragment(),
LocationListRecyclerViewAdapter.LocationSelectionRecyclerViewClickListener {

    private var listener: OnLocationListListener? = null
    lateinit var locationsRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "In onCreateView")
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

    companion object {

        private const val TAG = "LOCATION_LIST_FRAGMENT"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment LocationListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() : LocationListFragment {
            val fragment =
                LocationListFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
