package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
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

    lateinit var additionalReqOneImage : ImageView
    lateinit var additionalReqTwoImage : ImageView
    lateinit var additionalReqThreeImage : ImageView




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
        val adapter = ShowInfoClassesTakenRecyclerViewAdapter()
        var coreStatus = mutableMapOf("Theory" to false,
            "Software Design" to false,
            "System Design" to false)
        val coreStatusImageViews = mapOf("Theory" to R.id.theory_req_imageview,
            "Software Design" to R.id.software_design_req_imageview,
            "System Design" to R.id.system_design_req_imageview)

        val advancedCoursework = mapOf(degreePrograms[1] to 2,
            degreePrograms[2] to 4, degreePrograms[3] to 6)


        programName.text = programArray[preferences.getInt("degree_program", 0)]

        val programNameText = programName.text

        additionalReqOne = view!!.findViewById(R.id.additional_req_textview)
        additionalReqTwo = view!!.findViewById(R.id.additional_req_textview2)
        additionalReqThree = view!!.findViewById(R.id.additional_req_textview3)

        additionalReqOneImage = view!!.findViewById(R.id.additional_req_imageview)
        additionalReqTwoImage = view!!.findViewById(R.id.additional_req_imageview2)
        additionalReqThreeImage = view!!.findViewById(R.id.additional_req_imageview3)

        toolbarEdit.visibility = View.VISIBLE
        toolbarEdit.isClickable = true

        classesTakenRecyclerView = view!!.findViewById(R.id.show_info_classes_taken)

        classesTakenRecyclerView.adapter = adapter
        classesTakenRecyclerView.layoutManager = LinearLayoutManager(activity)

        showInfoViewModel.allClassesTaken.observe(this.viewLifecycleOwner, Observer { classesTaken ->
            classesTaken?.let {
                adapter.setClassesTaken(classesTaken)
                coreAreas.forEach { core ->
                    if (classesTaken?.stream().filter { classTaken ->
                            classTaken.courseRequirement == core
                        }.findFirst().isPresent) {
                        // Set Checkmark by core item
                        val image = activity?.findViewById<ImageView>(coreStatusImageViews[core]!!)
                        coreStatus[core] = true
                        image?.setImageResource(R.drawable.checkmark)
                    }
                    else {
                        val image = activity?.findViewById<ImageView>(coreStatusImageViews[core]!!)
                        coreStatus[core] = false
                        image?.setImageResource(R.drawable.cross_sign)
                    }
                }
            }
        })

        showInfoViewModel.advancedCourseworkTaken.observe(this.viewLifecycleOwner, Observer { advancedCourseworkTaken ->
            advancedCourseworkTaken?.let {
                // Find number of advanced credit hours required based on degree program
//                val programName = programName.text
                val advancedHoursReq = advancedCoursework[programNameText] ?: 0
                var advancedHoursTaken = advancedCourseworkTaken.count()
                showInfoViewModel.eightThousandLevelTaken.observe(this.viewLifecycleOwner, Observer { eightThousandTaken ->
//                    showInfoViewModel.sixThousandLevelTaken.observe(this.viewLifecycleOwner, Observer { sixThousandTaken ->
//                        // TODO: Used observables to check requirements
//                        //  Do I even need six thousand level?
//                        var sixThousandSatisfied = false
                        var eightThousandSatisfied = false
//
//                        /* TODO: Compare number of each level taken against number required for
//                         program */
//                    })

                    if (eightThousandTaken.count() > advancedHoursReq) {
                        eightThousandSatisfied = true
                    }

                    // Account for rule that if a class is used to satisfy a core requirement, it
                    // cannot be used as Advanced Coursework as well
                    if (coreStatus["Theory"] == true) {
                        advancedHoursTaken--
                    }
                    if (coreStatus["System Design"] == true) {
                        advancedHoursTaken--
                    }
                    if (coreStatus["Software Design"] == true) {
                        advancedHoursTaken--
                    }

                    // Set the image beside Advanced Coursework as a check mark or X depending
                    // on whether the student has taken enough courses
                    val image = activity?.findViewById<ImageView>(R.id.advanced_coursework_imageview)
                    System.out.println("AdvancedHours Taken =" + advancedHoursTaken + ", Advanced Hours Req = " + advancedHoursReq)
                    if (advancedHoursTaken >= advancedHoursReq!!) {
                        image?.setImageResource(R.drawable.checkmark)
                    }
                    else {
                        image?.setImageResource(R.drawable.cross_sign)
                    }
                })

//                var advancedHoursTaken = advancedCourseworkTaken.count()

            }
        })

        // Conditions for the M.S. Non-Thesis program
        if (programNameText == "M.S. Non-Thesis") {

            // Remove final two rows, as they are not needed, and set text of first one
            additionalReqTwo.isVisible = false
            additionalReqThree.isVisible = false
            additionalReqTwoImage.isVisible = false
            additionalReqThreeImage.isVisible = false

            additionalReqOne.text = "Masters Project"

            // Observe changes of mastersProjectTaken variable in ViewModel.
            // If count is greater than 0, the student has take the course and it should be marked
            // as satisfied
            showInfoViewModel.mastersProjectTaken.observe(this.viewLifecycleOwner, Observer { mastersProjectCount ->
                if (mastersProjectCount > 0) {
                    additionalReqOneImage.setImageResource(R.drawable.checkmark)
                } else {
                    additionalReqOneImage.setImageResource(R.drawable.cross_sign)
                }
            })
        }

        // Conditions for the M.S. Thesis program
        else if (programNameText == "M.S. Thesis") {

            // Set text for all additional requirements rows
            additionalReqOne.text = "Research Seminar"
            additionalReqTwo.text = "Masters Research"
            additionalReqThree.text = "Masters Thesis"

            // Observe changes of researchSeminarTaken variable in ViewModel.
            // If count is greater than 0, the student has take the course and it should be marked
            // as satisfied
            showInfoViewModel.researchSeminarTaken.observe(this.viewLifecycleOwner, Observer { researchSeminarCount ->
                if (researchSeminarCount > 0) {
                    additionalReqOneImage.setImageResource(R.drawable.checkmark)
                } else {
                    additionalReqOneImage.setImageResource(R.drawable.cross_sign)
                }
            })

            // Observe changes of mastersResearchTaken variable in ViewModel.
            // If count is greater than 0, the student has take the course and it should be marked
            // as satisfied
            showInfoViewModel.mastersResearchTaken.observe(this.viewLifecycleOwner, Observer { mastersResearchCount ->
                if (mastersResearchCount > 0) {
                    additionalReqTwoImage.setImageResource(R.drawable.checkmark)
                } else {
                    additionalReqTwoImage.setImageResource(R.drawable.cross_sign)
                }
            })

            // Observe changes of mastersThesisTaken variable in ViewModel.
            // If count is greater than 0, the student has take the course and it should be marked
            // as satisfied
            showInfoViewModel.mastersThesisTaken.observe(this.viewLifecycleOwner, Observer { mastersThesisCount ->
                if (mastersThesisCount > 0) {
                    additionalReqThreeImage.setImageResource(R.drawable.checkmark)
                } else {
                    additionalReqThreeImage.setImageResource(R.drawable.cross_sign)
                }
            })
        }

        // Conditions for the Ph.D program
        else if (programNameText == "Ph.D") {
            additionalReqOne.text = "Research Seminar"
            additionalReqTwo.text = "Additional Requirements"
            additionalReqThree.text = "Doctoral Dissertation"

            // Observe changes of researchSeminarTaken variable in ViewModel.
            // If count is greater than 0, the student has take the course and it should be marked
            // as satisfied
            showInfoViewModel.researchSeminarTaken.observe(this.viewLifecycleOwner, Observer { researchSeminarCount ->
                if (researchSeminarCount > 0) {
                    additionalReqOneImage.setImageResource(R.drawable.checkmark)
                } else {
                    additionalReqOneImage.setImageResource(R.drawable.cross_sign)
                }
            })

            // Observe changes of advancedCourseworkTaken variable in ViewModel.
            // Make adjustments for core courses and courses used for Advanced Coursework
            // as satisfied
            showInfoViewModel.advancedCourseworkTaken.observe(this.viewLifecycleOwner, Observer { advancedCoursework ->
                // TODO: Logic for checking if count is satisfactory
                var advancedHoursTaken = advancedCoursework.count()

                if (coreStatus["Theory"] == true) {
                    advancedHoursTaken--
                }
                if (coreStatus["System Design"] == true) {
                    advancedHoursTaken--
                }
                if (coreStatus["Software Design"] == true) {
                    advancedHoursTaken--
                }

                if (advancedCoursework.count() - 6 > 0) {
                    additionalReqTwoImage.setImageResource(R.drawable.checkmark)
                } else {
                    additionalReqTwoImage.setImageResource(R.drawable.cross_sign)
                }
            })

            showInfoViewModel.doctoralDissertationTaken.observe(this.viewLifecycleOwner, Observer { dissertationCount ->
                if (dissertationCount > 0) {
                    additionalReqThreeImage.setImageResource(R.drawable.checkmark)
                } else {
                    additionalReqThreeImage.setImageResource(R.drawable.cross_sign)
                }
            })
        }

    }

    override fun onDetach() {
        super.onDetach()
        toolbarEdit.visibility = View.INVISIBLE
        toolbarEdit.isClickable = false
    }

}
