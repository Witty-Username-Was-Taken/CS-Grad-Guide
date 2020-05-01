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
import android.widget.SearchView
import android.widget.Spinner
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.EditInfoViewModel

/**
 * A Fragment subclass used to create the Edit Info screen.
 * Serves as OnItemSelectedListener for Spinner object as well
 *
 * @property    editInfoViewModel           ViewModel used to update UI
 * @property    degreeProgramSpinner        Spinner used to select user degree program
 * @property    classesTakenRecyclerView    RecyclerView for classes taken
 * @property    allClassesRecyclerView      RecyclerView for class not yet taken
 * @property    classesTakenSearchView      SearchView for filtering classesTakenRecyclerView
 * @property    allClassesSearchView        SearchView for filtering allClassesRecyclerView
 * @property    preferences                 SharedPreferences for accessing simple stored data
 *
 * @author      Tripp Guinn
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
    private lateinit var preferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_info, container, false)
    }
    override fun onDetach() {
        super.onDetach()

        // Remove keyboard
        allClassesSearchView.clearFocus()
        classesTakenSearchView.clearFocus()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Get preferences to update and store degree program
        preferences = this.activity!!.getPreferences(Context.MODE_PRIVATE)

        // Create instance of view model
        editInfoViewModel = ViewModelProvider(this).get(EditInfoViewModel::class.java)

        // Set degree program initial position to previously selected item or first item in list
        // if nothing has been selected before
        val degreeProgram = preferences?.getInt("degree_program",0) ?: 0

        // Create adapters for both recycler views
        val classesTakenAdapter = ClassesTakenRecyclerViewAdapter(editInfoViewModel)
        val allClassesAdapter = AllClassesRecyclerViewAdapter(editInfoViewModel)

        // Connect Spinner adn set initial selection used degreeProgram
        degreeProgramSpinner = view!!.findViewById(R.id.degree_objective)
        degreeProgramSpinner.setSelection(degreeProgram,false)

        // Set Spinner behavior for item selection
        degreeProgramSpinner.onItemSelectedListener = this

        // Connect variables to RecyclerView UI
        classesTakenRecyclerView = view!!.findViewById(R.id.classes_taken)
        allClassesRecyclerView = view!!.findViewById(R.id.all_classes)

        // Set up adapters and layour for Recycler Views
        classesTakenRecyclerView.adapter = classesTakenAdapter
        classesTakenRecyclerView.layoutManager = LinearLayoutManager(activity)
        allClassesRecyclerView.adapter = allClassesAdapter
        allClassesRecyclerView.layoutManager = LinearLayoutManager(activity)

        // Connect SearchViews and determine behavior
        classesTakenSearchView = view!!.findViewById(R.id.classes_taken_searchview)
        classesTakenSearchView.setOnQueryTextListener(ClassesTakenQueryListener(this, classesTakenAdapter))
        classesTakenSearchView.setOnCloseListener(ClassesSearchViewCloseListener(classesTakenSearchView))

        allClassesSearchView = view!!.findViewById(R.id.remaining_classes_searchview)
        allClassesSearchView.setOnQueryTextListener(RemainingClassesQueryListener(this, allClassesAdapter))
        allClassesSearchView.setOnCloseListener(ClassesSearchViewCloseListener(allClassesSearchView))

        // Observe changes in classesNotTaken and allClassesTaken and update RecyclerView adapters
        // appropriately
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

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Do nothing
    }

    // Save user selection
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

        // Update classesTaken list in adapter based on full classesTaken list filtered by query
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

        // Update classesNotTaken list in adapter based on full classesNotTaken list filtered by query
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
