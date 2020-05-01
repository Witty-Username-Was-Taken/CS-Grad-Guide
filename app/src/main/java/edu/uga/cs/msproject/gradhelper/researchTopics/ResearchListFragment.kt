package edu.uga.cs.msproject.gradhelper.researchTopics

import android.content.Context
import android.net.Uri
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
import edu.uga.cs.msproject.gradhelper.dataObjects.Research
import edu.uga.cs.msproject.gradhelper.dataObjects.ResearchListViewModel

/**
 * Fragment used to display Research List screen.
 *
 * @property    professorListViewModel  ViewModel used to update UI
 */
class ResearchListFragment : Fragment(),
ResearchListRecyclerViewAdapter.ResearchSelectionRecyclerViewClickListener {

    lateinit var researchListRecyclerView: RecyclerView
    private lateinit var researchListViewModel : ResearchListViewModel
    private var listener: ResearchItemInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_research_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ResearchItemInteractionListener) {
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

        researchListRecyclerView = view!!.findViewById(R.id.research_list)
        val adapter = ResearchListRecyclerViewAdapter(this)

        researchListRecyclerView.adapter = adapter
        researchListRecyclerView.layoutManager = LinearLayoutManager(activity)

        researchListViewModel = ViewModelProvider(this).get(ResearchListViewModel::class.java)

        researchListViewModel.allResearch.observe(this, Observer { researchTopics ->
            researchTopics?.let { adapter.setResearchTopics(it) }
        })

    }

    override fun listItemClicked(research: Research) {
        listener?.onResearchItemClicked(research)
    }

    interface ResearchItemInteractionListener {
        // TODO: Update argument type and name
        fun onResearchItemClicked(research: Research)
    }


}
