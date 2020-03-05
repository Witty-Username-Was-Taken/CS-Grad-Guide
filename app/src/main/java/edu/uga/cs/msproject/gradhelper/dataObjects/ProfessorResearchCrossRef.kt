package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.room.*

@Entity(tableName = "professors_research", primaryKeys = ["id", "research_id"])
data class ProfessorResearchCrossRef(
    val id : String,
    val research_id : String
) {
}

data class ResearchWithProfessors(
    @Embedded val research : Research,
    @Relation(
        parentColumn = "research_id",
        entityColumn = "id",
        associateBy = Junction(ProfessorResearchCrossRef::class)
    )
    val professors : List<Professor>
) {
}