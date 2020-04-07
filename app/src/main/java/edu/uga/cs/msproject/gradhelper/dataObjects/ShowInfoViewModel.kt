package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShowInfoViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ProfessorRepository

    val allClassesTaken : LiveData<List<ClassTaken>>
    val advancedCourseworkTaken : LiveData<List<ClassTaken>>
    val researchSeminarTaken : LiveData<List<ClassTaken>>
    val mastersProjectTaken : LiveData<List<ClassTaken>>

    init {
        val professorDao = ProfessorDatabase.getDatabase(application, viewModelScope).professorDao()
        val researchDao = ProfessorDatabase.getDatabase(application, viewModelScope).researchDao()
        val classItemDao = ProfessorDatabase.getDatabase(application, viewModelScope).classItemDao()
        repository = ProfessorRepository(professorDao,researchDao,classItemDao)
        allClassesTaken = repository.allClassesTaken
        advancedCourseworkTaken = repository.advancedCourseworkTaken
    }
}