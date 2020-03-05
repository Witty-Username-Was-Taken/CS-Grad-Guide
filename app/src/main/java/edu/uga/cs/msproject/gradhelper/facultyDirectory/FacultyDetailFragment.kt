package edu.uga.cs.msproject.gradhelper.facultyDirectory

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.Professor

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FacultyDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FacultyDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FacultyDetailFragment : Fragment() {

    lateinit var prof : Professor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faculty_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prof = arguments?.getParcelable<Professor>("professorInfo")!!

        val prof_image = view!!.findViewById<ImageView>(R.id.professor_image)
        val prof_name =  view!!.findViewById<TextView>(R.id.professor_name)
        val prof_title =  view!!.findViewById<TextView>(R.id.professor_title)
        val prof_email =  view!!.findViewById<TextView>(R.id.email_content)
        val prof_office =  view!!.findViewById<TextView>(R.id.office_content)
        val prof_website =  view!!.findViewById<TextView>(R.id.website_content)
        val prof_research =  view!!.findViewById<TextView>(R.id.research_content)
        val directions_button =  view!!.findViewById<Button>(R.id.button)

        val resId = resources.getIdentifier(prof.image,
            "drawable", "edu.uga.cs.msproject.gradhelper")

        prof_image.setImageResource(resId)
        prof_name.text = prof.fname + " " + prof.lname
        prof_title.text = prof.title
        prof_email.text = prof.email
        prof_office.text = prof.office + " " + prof.building
        prof_website.text = prof.website
        prof_research.text = prof.research

        directions_button.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("https://plus.codes/WJWG+94 Athens, Georgia" /*+ prof.building*/)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        prof_website.setOnClickListener{
            val webpage: Uri = Uri.parse(prof.website)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(activity!!.packageManager) != null) {
                startActivity(intent)
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FacultyDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() : FacultyDetailFragment {
            return FacultyDetailFragment()
        }
    }
}
