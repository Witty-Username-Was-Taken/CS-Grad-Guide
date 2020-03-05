package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

class ResearchDetailViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ProfessorRepository
    val researchProfs : LiveData<List<ResearchWithProfessors>>
//    lateinit var researchProfessors: List<Professor>
//    lateinit var research = application.applicationContext

    init {
        val professorDao = ProfessorDatabase.getDatabase(application, viewModelScope).professorDao()
        val researchDao = ProfessorDatabase.getDatabase(application, viewModelScope).researchDao()
        val classItemDao = ProfessorDatabase.getDatabase(application, viewModelScope).classItemDao()
        repository = ProfessorRepository(professorDao,researchDao,classItemDao)
        researchProfs = repository.allCrossRefs
//        repository.getResearchProfessors(research.research_id)
//        researchProfessors = repository.researchProfessors
    }
}