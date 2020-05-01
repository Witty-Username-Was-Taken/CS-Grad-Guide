package edu.uga.cs.msproject.gradhelper.classList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassItem
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassListViewModel

/**
 * Fragment used to display full Class List.
 * Conforms to ClassSelectionRecyclerViewClickListener interface to send ClassItem object on user
 * interaction.
 *
 * @property    classListRecyclerView   RecyclerView used to display list items
 * @property    classListSearchView     SearchView used to filter class list
 * @property    classListViewModel      ViewModel containing observable class list
 * @property    listener                Used to listen for "clicks" on an item and pass ClassItem
 */
class ClassListFragment : Fragment(),
ClassListRecyclerViewAdapter.ClassSelectionRecyclerViewClickListener {

    lateinit var classListRecyclerView : RecyclerView
    lateinit var classListSearchView : SearchView
    private lateinit var classListViewModel: ClassListViewModel
    private var listener : OnClassListInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnClassListInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Connect components to UI Views
        classListRecyclerView = view!!.findViewById(R.id.class_list_recycler_view)
        classListSearchView = view!!.findViewById(R.id.class_list_search_view)

        // Create adapter for RecyclerView
        val adapter = ClassListRecyclerViewAdapter(this)

        // Set adapter and determine layout of RecyclerView
        classListRecyclerView.adapter = adapter
        classListRecyclerView.layoutManager = LinearLayoutManager(activity)

        // Create instance of ClassListViewModel
        classListViewModel = ViewModelProvider(this).get(ClassListViewModel::class.java)

        // Set custom OnQueryTextListener and OnCloseListener for SearchView
        classListSearchView.setOnQueryTextListener(ClassListQueryTextListener(this, adapter))
        classListSearchView.setOnCloseListener(SearchViewCloseListener(classListSearchView))

        // Observe changes to list of all classes, update class list when change occurs
        classListViewModel.allClasses.observe(this.viewLifecycleOwner, Observer { classes ->
            classes?.let { adapter.setClasses(classes) }
        })
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
    interface OnClassListInteractionListener {
        fun onClassListInteraction(classItem: ClassItem)
    }

    /**
     * Calls listeners onClassInteraction method
     *
     * @param   classItem   ClassItem that was selected by user
     */
    override fun classListItemClicked(classItem: ClassItem) {
        listener?.onClassListInteraction(classItem)
    }

    /**
     * OnQueryTextListener for SearchView filtering class list
     *
     * @param   fragment    Fragment this Class belongs to. Used to access viewLifecycleOwner
     * @param   adapter     ClassListRecyclerViewAdapter whose class list should be updated by search
     */
    inner class ClassListQueryTextListener(fragment: Fragment, adapter: ClassListRecyclerViewAdapter) :
        SearchView.OnQueryTextListener {

        val recyclerAdapter = adapter
        val localFragment = fragment

         // Not necessary
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        /**
         * Filter class list from view model using search input and set adapter's class list to the
         * result
         *
         * @param   query   Input to SearchView
         */
        override fun onQueryTextChange(query: String?): Boolean {

            // Use observer to interact with contents of LiveData List, allClasses and filter the
            // full class list each time the text changes
            classListViewModel.allClasses.observe(localFragment.viewLifecycleOwner, Observer { classes ->
                classes?.let { recyclerAdapter.setClasses(classes.filter { it.course_name.contains(query!!) }) }
            })

            return true
        }

    }

    // This is used to hide the keyboard when the 'X' button is clicked
    inner class SearchViewCloseListener(searchView : SearchView) : SearchView.OnCloseListener {
        val search = searchView

        override fun onClose(): Boolean {
            search.clearFocus()
            return true
        }
    }

}
