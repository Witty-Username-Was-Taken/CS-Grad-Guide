package edu.uga.cs.msproject.gradhelper.researchTopics

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.Professor
import edu.uga.cs.msproject.gradhelper.dataObjects.Research
import edu.uga.cs.msproject.gradhelper.dataObjects.ResearchDetailViewModel

/**
 * Fragment used to display Research Detail screen.
 *
 * @property    research    Research object used to update UI information
 */
class ResearchDetailFragment : Fragment(),
    ResearchFacultyRecyclerViewAdapter.FacultySelectionRecyclerViewClickListener {
    private var listener: OnResearchDetailFragmentInteractionListener? = null

    private lateinit var research : Research
    lateinit var facultyRecyclerView : RecyclerView
    private lateinit var researchDetailViewModel: ResearchDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_research_detail, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnResearchDetailFragmentInteractionListener) {
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

        research = arguments?.getParcelable<Research>("researchInfo")!!

        val researchName = view!!.findViewById<TextView>(R.id.researchName)
        val researchDesc =  view!!.findViewById<TextView>(R.id.researchDescContent)
        val adapter = ResearchFacultyRecyclerViewAdapter(this)
        val toolbar = activity!!.findViewById<Toolbar>(R.id.toolbar)

        toolbar.title = research.name

        researchDetailViewModel = ViewModelProvider(this).get(ResearchDetailViewModel::class.java)

        researchDetailViewModel.researchProfs.observe(this.viewLifecycleOwner, Observer { researchProfs ->
            researchProfs.let {
                for (x in it) {
                    if (x.research.research_id == research.research_id) {
                        adapter.setResearchFacultyList(x.professors)
                    }
                }
            }
        })

        facultyRecyclerView = view!!.findViewById<RecyclerView>(R.id.facultyGridRecyclerView)
        facultyRecyclerView.adapter = adapter
        facultyRecyclerView.layoutManager = GridLayoutManager(activity, 2)

        researchName.text = research.name
        researchDesc.text = research.description


    }

    override fun onListItemClicked(professor: Professor) {
        listener?.onFacultyItemInteraction(professor)
    }

    interface OnResearchDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFacultyItemInteraction(professor: Professor)
    }



}
