package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.lifecycle.LiveData

class ProfessorRepository(private val professorDao: ProfessorDao,
                          private val researchDao: ResearchDao,
                          private val classItemDao: ClassItemDao) {

    val allProfessors : LiveData<List<Professor>> = professorDao.getProfessors()
    val allResearch : LiveData<List<Research>> = researchDao.getResearchTopics()
    val allCrossRefs : LiveData<List<ResearchWithProfessors>> = professorDao.getResearchWithProfessors()
    val allClasses : LiveData<List<ClassItem>> = classItemDao.getClasses()

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


}