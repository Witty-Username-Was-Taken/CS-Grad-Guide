package edu.uga.cs.msproject.gradhelper.classList

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
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassListViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ClassListFragment.OnResearchListFragmentInteractionListener] interface
 * to handle interaction events.
 */
class ClassListFragment : Fragment() {

    lateinit var classListRecyclerView: RecyclerView
    private lateinit var classListViewModel: ClassListViewModel
    private var listenerResearchList: OnResearchListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_list, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listenerResearchList?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnResearchListFragmentInteractionListener) {
//            listenerResearchList = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

//    override fun onDetach() {
//        super.onDetach()
//        listenerResearchList = null
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        classListRecyclerView = view!!.findViewById(R.id.class_list_recycler_view)
        val adapter = ClassListRecyclerViewAdapter()

        classListRecyclerView.adapter = adapter
        classListRecyclerView.layoutManager = LinearLayoutManager(activity)

        classListViewModel = ViewModelProvider(this).get(ClassListViewModel::class.java)

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
    interface OnResearchListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}
