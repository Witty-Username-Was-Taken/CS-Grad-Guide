package edu.uga.cs.msproject.gradhelper.degreeTracker

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
import edu.uga.cs.msproject.gradhelper.dataObjects.EditInfoViewModel

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class EditInfoFragment : Fragment() {

    private lateinit var editInfoViewModel: EditInfoViewModel
    lateinit var classesTakenRecyclerView: RecyclerView
    lateinit var allClassesRecyclerView: RecyclerView
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_info, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        editInfoViewModel = ViewModelProvider(this).get(EditInfoViewModel::class.java)

        classesTakenRecyclerView = view!!.findViewById(R.id.classes_taken)
        allClassesRecyclerView = view!!.findViewById(R.id.all_classes)
        val classesTakenAdapter = ClassesTakenRecyclerViewAdapter(editInfoViewModel)
        val allClassesAdapter = AllClassesRecyclerViewAdapter(editInfoViewModel)

        classesTakenRecyclerView.adapter = classesTakenAdapter
        classesTakenRecyclerView.layoutManager = LinearLayoutManager(activity)
        allClassesRecyclerView.adapter = allClassesAdapter
        allClassesRecyclerView.layoutManager = LinearLayoutManager(activity)

        editInfoViewModel.classesNotTaken.observe(this.viewLifecycleOwner, Observer { allClasses ->
            allClasses?.let {
                allClassesAdapter.setAllClasses(allClasses)
            }
        })
        editInfoViewModel.allClassesTaken.observe(this.viewLifecycleOwner, Observer { allClasessTaken ->
            allClasessTaken?.let{
                classesTakenAdapter.setClassesTaken(allClasessTaken)
            }
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
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}
