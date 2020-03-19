package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ShowInfoViewModel

/**
 * A simple [Fragment] subclass.
 */
class ShowInfoFragment : Fragment() {

    private lateinit var showInfoViewModel: ShowInfoViewModel
    lateinit var classesTakenRecyclerView : RecyclerView
    lateinit var toolbarEdit : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val navController = findNavController()
        toolbarEdit = activity!!.findViewById<TextView>(R.id.edit_button)
        toolbarEdit.setOnClickListener{
            onDetach()
            navController.navigate(R.id.editInfoFragment)
        }

        showInfoViewModel = ViewModelProvider(this).get(ShowInfoViewModel::class.java)
        showInfoViewModel.allClassesTaken.observe(this.viewLifecycleOwner, Observer { classesTaken ->
            classesTaken?.let {
                if(classesTaken.isEmpty()) {
                    onDetach()
                    navController.navigate(R.id.editInfoFragment)
                }
            }
        })

        return inflater.inflate(R.layout.fragment_show_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val preferences = this.activity!!.getPreferences(Context.MODE_PRIVATE)
        val programArray = this.activity!!.resources.getStringArray(R.array.degree_types)
        val programName = view!!.findViewById<TextView>(R.id.program_name)
        programName.text = programArray[preferences.getInt("degree_program", 0)]

        toolbarEdit.visibility = View.VISIBLE
        toolbarEdit.isClickable = true

        classesTakenRecyclerView = view!!.findViewById(R.id.show_info_classes_taken)
        val adapter = ShowInfoClassesTakenRecyclerViewAdapter()

        classesTakenRecyclerView.adapter = adapter
        classesTakenRecyclerView.layoutManager = LinearLayoutManager(activity)

        showInfoViewModel.allClassesTaken.observe(this.viewLifecycleOwner, Observer { classesTaken ->
            classesTaken?.let {
                adapter.setClassesTaken(classesTaken)
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        toolbarEdit.visibility = View.INVISIBLE
        toolbarEdit.isClickable = false
    }

}
