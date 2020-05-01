package edu.uga.cs.msproject.gradhelper.dataObjects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * View Model used by ShowInfoFragment to access content of database and provide lists of all
 * classes taken as well as observe other information provided by the database to determine if
 * certain requirement have been met by the user for their degree program.
 *
 * @property    repository                  Instance of ProfessorRepository used to access LiveData from
 *                                          database.
 * @property    allClassesTaken             Observable list of all classes taken by user
 * @property    advancedCourseworkTaken     Observable list of classes taken that meet the advanced
 *                                          coursework criteria
 * @property    eightThousandLevelTaken     Observable list of classes taken bu user that are 8000 level
 * @property    mastersProjectTaken         Observable Int monitoring whether user has taken Masters Project
 * @property    mastersResearchTaken        Observable Int monitoring whether user has taken Masters Research
 * @property    mastersThesisTaken          Observable Int monitoring whether user has taken Masters Thesis
 * @property    doctoralDissertationTaken   Observable Int monitoring whether user has taken
 *                                          Doctoral Dissertation
 *
 * @author      Tripp Guinn
 */
class ShowInfoViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ProfessorRepository

    val allClassesTaken : LiveData<List<ClassTaken>>
    val advancedCourseworkTaken : LiveData<List<ClassTaken>>
    val eightThousandLevelTaken : LiveData<List<ClassTaken>>
    val researchSeminarTaken : LiveData<Int>
    val mastersProjectTaken : LiveData<Int>
    val mastersResearchTaken : LiveData<Int>
    val mastersThesisTaken : LiveData<Int>
    val doctoralDissertationTaken : LiveData<Int>


    init {
        val professorDao = ProfessorDatabase.getDatabase(application, viewModelScope).professorDao()
        val researchDao = ProfessorDatabase.getDatabase(application, viewModelScope).researchDao()
        val classItemDao = ProfessorDatabase.getDatabase(application, viewModelScope).classItemDao()
        repository = ProfessorRepository(professorDao,researchDao,classItemDao)
        allClassesTaken = repository.allClassesTaken
        advancedCourseworkTaken = repository.advancedCourseworkTaken
        eightThousandLevelTaken = repository.eightThousandLevelTaken
        researchSeminarTaken = repository.researchSeminarTaken
        mastersProjectTaken = repository.mastersProjectTaken
        mastersResearchTaken = repository.mastersResearchTaken
        mastersThesisTaken = repository.mastersThesisTaken
        doctoralDissertationTaken = repository.dissertationTaken
    }
}