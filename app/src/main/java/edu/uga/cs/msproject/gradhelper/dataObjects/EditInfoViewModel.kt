package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProfessorRepository

    val allClassesTaken : LiveData<List<ClassTaken>>
    val classesNotTaken : LiveData<List<ClassItem>>

    init {
        val professorDao = ProfessorDatabase.getDatabase(application, viewModelScope).professorDao()
        val researchDao = ProfessorDatabase.getDatabase(application, viewModelScope).researchDao()
        val classItemDao = ProfessorDatabase.getDatabase(application, viewModelScope).classItemDao()
        repository = ProfessorRepository(professorDao,researchDao,classItemDao)
        allClassesTaken = repository.allClassesTaken
        classesNotTaken = repository.classesNotTaken
    }

    internal fun addNewClassTaken(newClass: ClassItem) {
        val newClassTaken = ClassTaken(newClass.course_id, newClass.course_name, newClass.requirementSatisfied)
        // Use coroutine to execute database update
        GlobalScope.launch {
            repository.insertClassTaken(newClassTaken)
        }
    }

    internal fun removeClass(classTaken: ClassTaken) {
        GlobalScope.launch {
            repository.deleteClassTaken(classTaken)
        }
    }
}