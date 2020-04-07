package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

    enum class DegreeObjectives {
        MastersThesis,
        MastersNonThesis,
        Doctoral
    }

    private lateinit var showInfoViewModel: ShowInfoViewModel
    lateinit var classesTakenRecyclerView : RecyclerView
    lateinit var toolbarEdit : TextView

    lateinit var additionalReqOne : TextView
    lateinit var additionalReqTwo : TextView
    lateinit var additionalReqThree : TextView

    private val coreAreas = listOf<String>("Theory","System Design","Software Design")
    private lateinit var degreePrograms : Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        degreePrograms = resources.getStringArray(R.array.degree_types)

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

        additionalReqOne = view!!.findViewById(R.id.additional_req_textview)
        additionalReqTwo = view!!.findViewById(R.id.additional_req_textview2)
        additionalReqThree = view!!.findViewById(R.id.additional_req_textview3)

        toolbarEdit.visibility = View.VISIBLE
        toolbarEdit.isClickable = true

        classesTakenRecyclerView = view!!.findViewById(R.id.show_info_classes_taken)
        val adapter = ShowInfoClassesTakenRecyclerViewAdapter()

        classesTakenRecyclerView.adapter = adapter
        classesTakenRecyclerView.layoutManager = LinearLayoutManager(activity)

        val coreStatus = mapOf("Theory" to R.id.theory_req_imageview,
            "Software Design" to R.id.software_design_req_imageview,
            "System Design" to R.id.system_design_req_imageview)


        showInfoViewModel.allClassesTaken.observe(this.viewLifecycleOwner, Observer { classesTaken ->
            classesTaken?.let {
                adapter.setClassesTaken(classesTaken)
                coreAreas.forEach { core ->
                    System.out.println("Core: " + core)
                    if (classesTaken?.stream().filter { classTaken ->
                            classTaken.courseRequirement == core
                        }.findFirst().isPresent) {
                        // Set Checkmark by core item
                        System.out.println("Found core!")
                        val image = activity?.findViewById<ImageView>(coreStatus[core]!!)
                        image?.setImageResource(R.drawable.checkmark)
                    }
                    else {
                        val image = activity?.findViewById<ImageView>(coreStatus[core]!!)
                        image?.setImageResource(R.drawable.cross_sign)
                    }
                }
            }
        })

        val advancedCoursework = mapOf(degreePrograms[1] to 2,
            degreePrograms[2] to 4, degreePrograms[3] to 6)

        showInfoViewModel.advancedCourseworkTaken.observe(this.viewLifecycleOwner, Observer { advancedCourseworkTaken ->
            advancedCourseworkTaken?.let {
                // Find number of advanced credit hours required based on degree program
                val test = programName.text
                val advancedHoursReq = advancedCoursework[test] ?: 0
                val advancedHoursTaken = advancedCourseworkTaken.count()
                val image = activity?.findViewById<ImageView>(R.id.additional_req_imageview)

                if (advancedHoursTaken >= advancedHoursReq!!) {
                    image?.setImageResource(R.drawable.checkmark)
                }
                else {
                    image?.setImageResource(R.drawable.cross_sign)
                }
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        toolbarEdit.visibility = View.INVISIBLE
        toolbarEdit.isClickable = false
    }

}
