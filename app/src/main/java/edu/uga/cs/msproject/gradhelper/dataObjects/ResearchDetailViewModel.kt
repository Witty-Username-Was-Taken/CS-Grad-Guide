package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

/**
 * View Model used by ResearchDetailFragment to access content of database and provide the list of
 * professors used by ResearchFacultyRecyclerViewAdapter. ProfessorRepository is used to obtain an
 * Observable list of all ResearchWithProfessors objects within our database.
 * @property    repository      Instance of ProfessorRepository used to access LiveData from
 *                              database.
 * @property    researchProfs   Observable list of all research topics and the professors with an
 *                              interest in that research.
 *
 * @author      Tripp Guinn
 */
class ResearchDetailViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ProfessorRepository
    val researchProfs : LiveData<List<ResearchWithProfessors>>

    init {
        val professorDao = ProfessorDatabase.getDatabase(application, viewModelScope).professorDao()
        val researchDao = ProfessorDatabase.getDatabase(application, viewModelScope).researchDao()
        val classItemDao = ProfessorDatabase.getDatabase(application, viewModelScope).classItemDao()
        repository = ProfessorRepository(professorDao,researchDao,classItemDao)
        researchProfs = repository.allCrossRefs
    }
}