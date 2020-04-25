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
import kotlinx.android.synthetic.main.activity_main.*

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

        classListRecyclerView = view!!.findViewById(R.id.class_list_recycler_view)
        classListSearchView = view!!.findViewById(R.id.class_list_search_view)
        val adapter = ClassListRecyclerViewAdapter(this)

        classListRecyclerView.adapter = adapter
        classListRecyclerView.layoutManager = LinearLayoutManager(activity)

        classListViewModel = ViewModelProvider(this).get(ClassListViewModel::class.java)
        classListSearchView.setOnQueryTextListener(QueryTextListener(this, adapter))
        classListSearchView.setOnCloseListener(SearchViewCloseListener(classListSearchView))

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
        // TODO: Update argument type and name
        fun onClassListInteraction(classItem: ClassItem)
    }

    override fun classListItemClicked(classItem: ClassItem) {
        listener?.onClassListInteraction(classItem)
    }

    inner class QueryTextListener(fragment: Fragment, adapter: ClassListRecyclerViewAdapter) : SearchView.OnQueryTextListener {

        val recyclerAdapter = adapter
        val localFragment = fragment

        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            classListViewModel.allClasses.observe(localFragment.viewLifecycleOwner, Observer { classes ->
                classes?.let { recyclerAdapter.setClasses(classes.filter { it.course_name.contains(query!!) }) }
            })

            return true
        }

    }

    inner class SearchViewCloseListener(searchView : SearchView) : SearchView.OnCloseListener {
        val search = searchView

        override fun onClose(): Boolean {
            search.clearFocus()
            return true
        }
    }

}
