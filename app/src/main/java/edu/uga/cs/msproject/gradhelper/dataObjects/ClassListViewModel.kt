package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * View Model used by ClassListFragment to access content of database and provide the list of
 * classes used by ClassListRecyclerViewAdapter. ProfessorRepository is used to obtain an Observable
 * list of all classes within our database.
 * @property    repository  Instance of ProfessorRepository used to access LiveData from
 *                          database.
 * @property    allClasses  Observable list of all classes in database.
 * @author      Tripp Guinn
 */
class ClassListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : ProfessorRepository
    val allClasses : LiveData<List<ClassItem>>

    // Obtain DAO objects from ProfessorDatabase and use them to create a ProfessorRepository and
    // access its allClasses variable
    init {
        val professorDao = ProfessorDatabase.getDatabase(application, viewModelScope).professorDao()
        val researchDao = ProfessorDatabase.getDatabase(application, viewModelScope).researchDao()
        val classItemDao = ProfessorDatabase.getDatabase(application, viewModelScope).classItemDao()
        repository = ProfessorRepository(professorDao,researchDao,classItemDao)
        allClasses = repository.allClasses
    }

    /**
     * Used to insert ClassItem object using ViewModel
     */
    fun insert(classItem: ClassItem) = viewModelScope.launch {
        repository.insertClass(classItem)
    }


}