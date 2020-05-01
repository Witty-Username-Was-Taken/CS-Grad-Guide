package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * View Model used by ResearchListFragment to access content of database and provide the list of
 * research topics used by ResearchListRecyclerViewAdapter. ProfessorRepository is used to obtain an
 * Observable list of all Research objects within our database.
 *
 * @property    repository      Instance of ProfessorRepository used to access LiveData from
 *                              database.
 * @property    allResearch     Observable list of all research topics
 *
 * @author      Tripp Guinn
 */
class ResearchListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : ProfessorRepository

    val allResearch : LiveData<List<Research>>

    init {
        val professorDao = ProfessorDatabase.getDatabase(application, viewModelScope).professorDao()
        val researchDao = ProfessorDatabase.getDatabase(application, viewModelScope).researchDao()
        val classItemDao = ProfessorDatabase.getDatabase(application, viewModelScope).classItemDao()
        repository = ProfessorRepository(professorDao,researchDao,classItemDao)
        allResearch= repository.allResearch
    }

    fun insert(research: Research) = viewModelScope.launch {
        repository.insertResearch(research)
    }
}