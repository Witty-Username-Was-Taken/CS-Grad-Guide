package edu.uga.cs.msproject.gradhelper.facultyDirectory


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.Professor
import edu.uga.cs.msproject.gradhelper.dataObjects.ProfessorListViewModel
import edu.uga.cs.msproject.gradhelper.locationFinder.DirectoryListRecyclerViewAdapter

/**
 * Fragment used to display Directory List screen.
 *
 * @property    professorListViewModel  ViewModel used to update UI
 */
class DirectoryListFragment : Fragment(),
DirectoryListRecyclerViewAdapter.DirectorySelectionRecyclerViewClickListener {

    lateinit var directoryRecyclerView : RecyclerView
    private lateinit var professorListViewModel: ProfessorListViewModel
    private var listener : OnDirectoryItemFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDirectoryItemFragmentInteractionListener) {
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
        return inflater.inflate(R.layout.fragment_directory_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        directoryRecyclerView = view!!.findViewById(R.id.directory_list_recyclerview)
        val adapter = DirectoryListRecyclerViewAdapter(this)

        directoryRecyclerView.adapter = adapter
        directoryRecyclerView.layoutManager = LinearLayoutManager(activity)

        professorListViewModel = ViewModelProvider(this).get(ProfessorListViewModel::class.java)

        professorListViewModel.allProfessors.observe(this.viewLifecycleOwner, Observer { professors ->
            // Update the cached copy of the words in the adapter.
            professors?.let { adapter.setProfessors(it) }
        })
    }

    override fun listItemClicked(professor: Professor) {
        listener?.onDirectoryItemClicked(professor)
    }

    interface OnDirectoryItemFragmentInteractionListener {
        fun onDirectoryItemClicked(professor: Professor)
    }
}
