package edu.uga.cs.msproject.gradhelper.degreeTracker

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.EditInfoViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class EditInfoFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        private val TAG = "EDIT_INFO"
    }

    private lateinit var editInfoViewModel: EditInfoViewModel
    lateinit var degreeProgramSpinner : Spinner
    lateinit var classesTakenRecyclerView : RecyclerView
    lateinit var allClassesRecyclerView : RecyclerView
    lateinit var classesTakenSearchView : SearchView
    lateinit var allClassesSearchView : SearchView
    private var listener : OnFragmentInteractionListener? = null
    private lateinit var preferences : SharedPreferences

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

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = this.activity!!.getPreferences(Context.MODE_PRIVATE)

        editInfoViewModel = ViewModelProvider(this).get(EditInfoViewModel::class.java)

        val degreeProgram = preferences?.getInt("degree_program",0) ?: 0
        val classesTakenAdapter = ClassesTakenRecyclerViewAdapter(editInfoViewModel)
        val allClassesAdapter = AllClassesRecyclerViewAdapter(editInfoViewModel)

        degreeProgramSpinner = view!!.findViewById(R.id.degree_objective)
        degreeProgramSpinner.setSelection(degreeProgram,false)

        degreeProgramSpinner.onItemSelectedListener = this

        classesTakenRecyclerView = view!!.findViewById(R.id.classes_taken)
        allClassesRecyclerView = view!!.findViewById(R.id.all_classes)

        classesTakenRecyclerView.adapter = classesTakenAdapter
        classesTakenRecyclerView.layoutManager = LinearLayoutManager(activity)
        allClassesRecyclerView.adapter = allClassesAdapter
        allClassesRecyclerView.layoutManager = LinearLayoutManager(activity)

        classesTakenSearchView = view!!.findViewById(R.id.classes_taken_searchview)
        classesTakenSearchView.setOnQueryTextListener(ClassesTakenQueryListener(this, classesTakenAdapter))
        classesTakenSearchView.setOnCloseListener(ClassesSearchViewCloseListener(classesTakenSearchView))

        allClassesSearchView = view!!.findViewById(R.id.remaining_classes_searchview)
        allClassesSearchView.setOnQueryTextListener(RemainingClassesQueryListener(this, allClassesAdapter))
        allClassesSearchView.setOnCloseListener(ClassesSearchViewCloseListener(allClassesSearchView))

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

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d(TAG, "Item Selected: " + p3)
        preferences?.edit {
            putInt("degree_program", p2)
            Log.d(TAG, "putInt Used")
            commit()
        }
    }

    inner class ClassesTakenQueryListener(fragment: Fragment, adapter: ClassesTakenRecyclerViewAdapter) :
        SearchView.OnQueryTextListener {

        val hostFragment = fragment
        val classesTakenAdapter = adapter

        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            editInfoViewModel.allClassesTaken.observe(hostFragment.viewLifecycleOwner, Observer { classesTaken ->
                classesTaken?.let { classesTakenAdapter.setClassesTaken(classesTaken.filter { it.className.contains(query ?: "") }) }
            })
            return true
        }
    }

    inner class RemainingClassesQueryListener(fragment: Fragment, adapter: AllClassesRecyclerViewAdapter) :
        SearchView.OnQueryTextListener {

        val hostFragment = fragment
        val allClassesAdapter = adapter

        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            editInfoViewModel.classesNotTaken.observe(hostFragment.viewLifecycleOwner, Observer { classesNotTaken ->
                classesNotTaken?.let { allClassesAdapter.setAllClasses(classesNotTaken.filter { it.course_name.contains(query ?: "")}) }
            })
            return true
        }
    }

    inner class ClassesSearchViewCloseListener(searchView : SearchView) : SearchView.OnCloseListener {
        val search = searchView

        override fun onClose(): Boolean {
            search.clearFocus()
            return true
        }
    }

}
