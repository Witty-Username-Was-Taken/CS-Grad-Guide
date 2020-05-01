package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.lifecycle.LiveData

/**
 *  Class for observing data from within database. Observable variables from this class are accessed
 *  by View Model classes to be used by their respective Fragments.
 *
 *  @param      professorDao            DAO for data relating to Professors
 *  @param      researchDao             DAO for data relating to Research
 *  @param      classItemDao            DAO for data relating to Classes
 *
 *  @property   allProfessors           Observable list of all professors in database
 *  @property   allResearch             Observable list of all research items in database
 *  @property   allCrossRefs            Observable list of all research items and professors with
 *                                      interests in that research in database
 *  @property   allClasses              Observable list of all classes in database
 *  @property   allClassesTaken         Observable list of classes user has marked as taken
 *  @property   classesNotTaken         Observable list of classes user has not marked as taken
 *  @property   advancedCourseworkTaken Observable list of classes taken that satisfy the advanced
 *                                      coursework requirement
 *  @property   sixThousandLevelTaken   Observable list of classes taken that satisfy 6000 level
 *                                      course requirements
 *  @property   eightThousandLevelTaken Observable list of classes taken that satisfy 8000 level
 *                                      course requirements
 *  @property   researchSeminarTaken    Observable Int tracking if Research Seminar has been taken
 *  @property   mastersProjectTaken     Observable Int tracking if Masters Project has been taken
 *  @property   mastersResearchTaken    Observable Int tracking if Masters Research has been taken
 *  @property   mastersThesisTaken      Observable Int tracking if Masters Thesis has been taken
 *  @property   dissertationTaken       Observable Int tracking if Doctoral Dissertation has been taken
 *
 *  @author     Tripp Guinn
 */
class ProfessorRepository(private val professorDao: ProfessorDao,
                          private val researchDao: ResearchDao,
                          private val classItemDao: ClassItemDao) {

    val allProfessors : LiveData<List<Professor>> = professorDao.getProfessors()
    val allResearch : LiveData<List<Research>> = researchDao.getResearchTopics()
    val allCrossRefs : LiveData<List<ResearchWithProfessors>> = professorDao.getResearchWithProfessors()
    val allClasses : LiveData<List<ClassItem>> = classItemDao.getClasses()
    val allClassesTaken : LiveData<List<ClassTaken>> = classItemDao.getClassesTaken()
    val classesNotTaken : LiveData<List<ClassItem>> = classItemDao.getClassesNotTaken()
    val advancedCourseworkTaken : LiveData<List<ClassTaken>> = classItemDao.getAdvancedCourseworkTaken()
    val sixThousandLevelTaken : LiveData<List<ClassTaken>> = classItemDao.getSixThousandLevelTaken()
    val eightThousandLevelTaken : LiveData<List<ClassTaken>> = classItemDao.getEightThousandLevelTaken()
    val researchSeminarTaken : LiveData<Int> = classItemDao.getResearchSeminarTaken()
    val mastersProjectTaken : LiveData<Int> = classItemDao.getMastersProjectTaken()
    val mastersResearchTaken : LiveData<Int> = classItemDao.getMastersResearchTaken()
    val mastersThesisTaken : LiveData<Int> = classItemDao.getMastersThesisTaken()
    val dissertationTaken : LiveData<Int> = classItemDao.getDoctoralDissertationTaken()

    /**
     * Repository method to call DAO function to insert Professor
     *
     * @param   professor   Professor object to be inserted into database
     */
    suspend fun insertProfessor(professor: Professor) {
        professorDao.insert(professor)
    }

    /**
     * Repository method to call DAO function to insert Research topic
     *
     * @param   research    Research object to be inserted into database
     */
    suspend fun insertResearch(research: Research) {
        researchDao.insert(research)
    }

    /**
     * Repository method to call DAO function to insert ClassItem
     *
     * @param   classItem   ClassItem object to be inserted into database
     */
    suspend fun insertClass(classItem: ClassItem) {
        classItemDao.insertClass(classItem)
    }

    /**
     * Repository method to call DAO function to insert ClassTaken
     *
     * @param   classTaken  ClassTaken object to be inserted into database
     */
    suspend fun insertClassTaken(classTaken: ClassTaken) {
        classItemDao.insertClassTaken(classTaken)
    }

    /**
     * Repository method to call DAO function to delete Professor from Class_Taken table
     *
     * @param   classTaken  ClassTaken object to be deleted from database
     */
    suspend fun deleteClassTaken(classTaken: ClassTaken) {
        classItemDao.deleteClassTaken(classTaken)
    }
}