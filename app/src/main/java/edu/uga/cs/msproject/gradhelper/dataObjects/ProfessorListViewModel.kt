package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * View Model used by DirectoryListFragment to access content of database and provide the list of
 * classes used by DirectoryListRecyclerViewAdapter. ProfessorRepository is used to obtain an Observable
 * list of all professors within our database.
 * @property    repository      Instance of ProfessorRepository used to access LiveData from
 *                              database.
 * @property    allProfessors   Observable list of all professors in database.
 * @author      Tripp Guinn
 */
class ProfessorListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProfessorRepository
    val allProfessors: LiveData<List<Professor>>

    init {
        val professorDao = ProfessorDatabase.getDatabase(application, viewModelScope).professorDao()
        val researchDao = ProfessorDatabase.getDatabase(application, viewModelScope).researchDao()
        val classItemDao = ProfessorDatabase.getDatabase(application, viewModelScope).classItemDao()
        repository = ProfessorRepository(professorDao,researchDao,classItemDao)
        allProfessors = repository.allProfessors
    }

    /**
     * Used to insert Professor object using ViewModel
     */
    fun insert(professor: Professor) = viewModelScope.launch {
        repository.insertProfessor(professor)
    }
}