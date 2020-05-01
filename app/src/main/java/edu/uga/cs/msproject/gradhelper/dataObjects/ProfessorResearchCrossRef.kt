package edu.uga.cs.msproject.gradhelper.dataObjects

import androidx.room.*

/**
 * This Class is used as an Entity for the database. It represents the association table between
 * Professors and Research_Topics, since a professor may have more than one research interest and
 * multiple professors may have interests in a single research topic. This table is built from
 * research_profs.csv.
 *
 * @param   id              Primary Key from Professor
 * @param   research_id     Primary Key from Rearch_Topic
 *
 * @author  Tripp Guinn
 */
@Entity(tableName = "professors_research", primaryKeys = ["id", "research_id"])
data class ProfessorResearchCrossRef(
    val id : String,
    val research_id : String
) {
}

/**
 * Data class used to simplify results of query to associate professors with research topics.
 *
 * @param   research    Research object
 * @param   professors  List of professors who have interest in research topic
 */
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