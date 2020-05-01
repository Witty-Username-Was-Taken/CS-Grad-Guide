package edu.uga.cs.msproject.gradhelper.classList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassItem



/**
 * Fragment used to display Class Detail screen.
 *
 * @property    classItem   ClassItem object used to populate information about a course on screen
 */
class ClassDetailFragment : Fragment() {

    lateinit var classItem : ClassItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_item_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Get the ClassItem the user selected from the arguments passed in
        classItem = arguments?.getParcelable<ClassItem>("class_info")!!

        // Connect variable to UI Views
        val className = activity!!.findViewById<TextView>(R.id.className)
        val semestersOffered = activity!!.findViewById<TextView>(R.id.semestersOfferedInfo)
        val courseDesc = activity!!.findViewById<TextView>(R.id.courseDescription)

        // Set text based on information from selected ClassItem
        className.text = classItem.course_name
        semestersOffered.text = classItem.semesterOffered
        courseDesc.text = classItem.description
    }
}
