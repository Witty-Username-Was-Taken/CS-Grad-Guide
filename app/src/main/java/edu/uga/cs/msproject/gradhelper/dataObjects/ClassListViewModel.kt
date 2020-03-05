package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ClassListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : ProfessorRepository
    val allClasses : LiveData<List<ClassItem>>

    init {
        val professorDao = ProfessorDatabase.getDatabase(application, viewModelScope).professorDao()
        val researchDao = ProfessorDatabase.getDatabase(application, viewModelScope).researchDao()
        val classItemDao = ProfessorDatabase.getDatabase(application, viewModelScope).classItemDao()
        repository = ProfessorRepository(professorDao,researchDao,classItemDao)
        allClasses = repository.allClasses
    }

    fun insert(classItem: ClassItem) = viewModelScope.launch {
        repository.insertClass(classItem)
    }


}