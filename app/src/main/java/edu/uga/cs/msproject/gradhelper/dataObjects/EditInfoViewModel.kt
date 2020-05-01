package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * View Model used by EditInfoFragment to access content of database and provide the lists of
 * classes marked as taken by the user and those that have not been for use in
 * ClassesTakenRecyclerViewAdapter and AllClassesRecyclerViewAdapter, respectively.
 * ProfessorRepository is used to obtain Observable lists classes that have and have not been taken
 * by the user.
 *
 * @property    repository      Instance of ProfessorRepository used to access LiveData from
 *                              database.
 * @property    allClassesTaken Observable list of all classes user has marked as taken.
 * @property    classesNotTaken Observable list of classes that user has not marked as taken.
 * @author      Tripp Guinn
 */
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

    /**
     * Adds a class to the Classes_Taken table
     *
     * @param   newClass    ClassItem selected by user has having been taken by them.
     */
    internal fun addNewClassTaken(newClass: ClassItem) {
        val newClassTaken = ClassTaken(newClass.course_id, newClass.course_name, newClass.requirementSatisfied)
        // Use coroutine to execute database update
        GlobalScope.launch {
            repository.insertClassTaken(newClassTaken)
        }
    }

    /**
     * Removes a class from the Classes_Taken table
     *
     * @param   classTaken  ClassTaken object selected by user to be marked as "not taken".
     */
    internal fun removeClass(classTaken: ClassTaken) {
        GlobalScope.launch {
            repository.deleteClassTaken(classTaken)
        }
    }
}