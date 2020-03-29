package edu.uga.cs.msproject.gradhelper.dataObjects

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.opencsv.CSVReader
import edu.uga.cs.msproject.gradhelper.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.lang.Exception

@Database(entities = arrayOf(Professor::class,Research::class,ProfessorResearchCrossRef::class,
    ClassItem::class, ClassTaken::class), version = 1, exportSchema = false)
abstract class ProfessorDatabase : RoomDatabase() {

    abstract fun professorDao() : ProfessorDao
    abstract fun researchDao() : ResearchDao
    abstract fun classItemDao() : ClassItemDao

    companion object {

        final val TAG = "DATABASE"

        @Volatile
        private var INSTANCE: ProfessorDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ) : ProfessorDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProfessorDatabase::class.java,
                    "professor_database"
                )
                    .addCallback(ProfessorDatabaseCallback(scope, context.resources))
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private class ProfessorDatabaseCallback(
            private val scope: CoroutineScope,
            private val res: Resources
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.professorDao(), database.researchDao(),
                            database.classItemDao(), res)
                    }
                }
            }

            suspend fun populateDatabase(professorDao: ProfessorDao, researchDao: ResearchDao,
                                         classItemDao: ClassItemDao, res: Resources) {

                System.out.println("ProfessorDatabase populateDatabase!")

                // Populate Professors table
                if (professorDao.professorsCount() == 0) {
                    try {
                        var csvReader =
                            CSVReader(InputStreamReader(res.openRawResource(R.raw.professor_info)))

                        var nextLine: Array<String> = csvReader.readNext()

                        while (nextLine != null) {
                            val newProfessor = Professor(
                                nextLine.get(0),
                                nextLine.get(1),
                                nextLine.get(2),
                                nextLine.get(3),
                                nextLine.get(4),
                                nextLine.get(5),
                                nextLine.get(6),
                                nextLine.get(7),
                                nextLine.get(8),
                                nextLine.get(9),
                                nextLine.get(10)
                            )
                            professorDao.insert(newProfessor)
                            nextLine = csvReader.readNext()
                        }

                        System.out.println("ProfessorDatabase done with CSV!")
                    } catch (e: Exception) {

                    }
                }
                else {
                    System.out.println("Professor Count: " + professorDao.professorsCount())
                }

                // Populate Research table
                if (researchDao.researchCount() == 0) {
                    try {
                        var csvReader =
                            CSVReader(InputStreamReader(res.openRawResource(R.raw.research_list)))

                        var nextLine: Array<String> = csvReader.readNext()

                        while (nextLine != null) {
                            val newResearch =
                                Research(nextLine.get(0), nextLine.get(1), nextLine.get(2))
                            researchDao.insert(newResearch)
                            nextLine = csvReader.readNext()
                        }

                    } catch (e: Exception) {

                    }
                }

                // Populate ResearchProf table
                if (professorDao.profResearchCount() == 0) {
                    try {
                        var csvReader =
                            CSVReader(InputStreamReader(res.openRawResource(R.raw.research_profs)))

                        var nextLine: Array<String> = csvReader.readNext()

                        while (nextLine != null) {
                            val newResearchProf =
                                ProfessorResearchCrossRef(nextLine.get(1), nextLine.get(0))
                            professorDao.insertResearchProf(newResearchProf)
                            Log.d(TAG, newResearchProf.id)
                            nextLine = csvReader.readNext()
                        }

                    } catch (e: Exception) {
                        Log.d(TAG, e.toString())
                    }
                }

                // Populate Classes table
                if (classItemDao.classesCount() == 0) {
                    try {
                        var csvReader =
                            CSVReader(InputStreamReader(res.openRawResource(R.raw.class_list)))

                        var nextLine: Array<String> = csvReader.readNext()

                        while (nextLine != null) {
                            val newClass = ClassItem(
                                nextLine.get(0),
                                nextLine.get(1),
                                nextLine.get(2),
                                nextLine.get(3),
                                nextLine.get(4)
                            )
                            classItemDao.insertClass(newClass)
                            Log.d(TAG, newClass.course_id.toString())
                            nextLine = csvReader.readNext()
                        }

                    } catch (e: Exception) {
                        Log.d(TAG, e.toString())
                    }
                }
            }
        }
    }
}