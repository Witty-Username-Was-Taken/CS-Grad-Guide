package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.lifecycle.LiveData

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

    lateinit var researchProfessors : List<Professor>

    suspend fun insertProfessor(professor: Professor) {
        professorDao.insert(professor)
    }

    suspend fun insertResearch(research: Research) {
        researchDao.insert(research)
    }

    suspend fun insertClass(classItem: ClassItem) {
        classItemDao.insertClass(classItem)
    }

    suspend fun insertClassTaken(classTaken: ClassTaken) {
        classItemDao.insertClassTaken(classTaken)
    }

    suspend fun deleteClassTaken(classTaken: ClassTaken) {
        classItemDao.deleteClassTaken(classTaken)
    }
}