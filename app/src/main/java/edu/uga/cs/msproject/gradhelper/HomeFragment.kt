package edu.uga.cs.msproject.gradhelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    lateinit var locationsImage : ImageView
    lateinit var classListImage : ImageView
    lateinit var directoryImage : ImageView
    lateinit var researchImage : ImageView
    lateinit var degreeTrackerImage : ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navController = findNavController()

        locationsImage = activity!!.findViewById(R.id.locationsImage)
        classListImage = activity!!.findViewById(R.id.classListImage)
        directoryImage = activity!!.findViewById(R.id.directoryImage)
        researchImage = activity!!.findViewById(R.id.researchImage)
        degreeTrackerImage = activity!!.findViewById(R.id.degreeTrackerImage)

        locationsImage.setOnClickListener {
            navController.navigate(R.id.locationListFragment)
        }

        classListImage.setOnClickListener {
            navController.navigate(R.id.classListFragment)
        }

        directoryImage.setOnClickListener {
            navController.navigate(R.id.directoryListFragment)
        }

        researchImage.setOnClickListener {
            navController.navigate(R.id.researchListFragment)
        }

        degreeTrackerImage.setOnClickListener {
            navController.navigate(R.id.editInfoFragment)
        }
    }
}