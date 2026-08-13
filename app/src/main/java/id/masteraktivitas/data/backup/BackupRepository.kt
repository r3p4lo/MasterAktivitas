package id.masteraktivitas.data.backup

import android.content.Context
import androidx.room.withTransaction
import com.google.gson.Gson
import id.masteraktivitas.data.local.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

data class BackupData(
    val categories: List<CategoryEntity>? = null,
    val subcategories: List<SubcategoryEntity>? = null,
    val projects: List<ProjectEntity>? = null,
    val milestones: List<MilestoneEntity>? = null,
    val tasks: List<TaskEntity>? = null,
    val subtasks: List<SubtaskEntity>? = null,
    val experiments: List<ExperimentEntity>? = null,
    val transactions: List<TransactionEntity>? = null,
    val assets: List<AssetEntity>? = null,
    val learningSessions: List<LearningSessionEntity>? = null,
    val skills: List<SkillEntity>? = null,
    val habits: List<HabitEntity>? = null,
    val habitLogs: List<HabitLogEntity>? = null,
    val notes: List<NoteEntity>? = null,
    val calendarEvents: List<CalendarEventEntity>? = null,
    val risks: List<RiskEntity>? = null,
    val settings: List<SettingsEntity>? = null
)

class BackupRepository(
    private val context: Context,
    private val db: AppDatabase
) {
    private val gson = Gson()

    private fun backupFile(): File {
        val dir = context.getExternalFilesDir(null) ?: context.filesDir
        return File(dir, "masteraktivitas-backup.json")
    }

    suspend fun exportJson(): File = withContext(Dispatchers.IO) {
        val data = BackupData(
            categories = db.categoryDao().getAll(),
            subcategories = db.subcategoryDao().getAll(),
            projects = db.projectDao().getAll(),
            milestones = db.milestoneDao().getAll(),
            tasks = db.taskDao().getAll(),
            subtasks = db.subtaskDao().getAll(),
            experiments = db.experimentDao().getAll(),
            transactions = db.transactionDao().getAll(),
            assets = db.assetDao().getAll(),
            learningSessions = db.learningDao().getAll(),
            skills = db.skillDao().getAll(),
            habits = db.habitDao().getAll(),
            habitLogs = db.habitDao().getAllLogs(),
            notes = db.noteDao().getAll(),
            calendarEvents = db.calendarEventDao().getAll(),
            risks = db.riskDao().getAll(),
            settings = db.settingsDao().getAll()
        )

        val file = backupFile()
        file.writeText(gson.toJson(data))
        file
    }

    suspend fun importDefault() {
        importJson(backupFile())
    }

    suspend fun importJson(file: File) = withContext(Dispatchers.IO) {
        val json = file.readText()
        val data = gson.fromJson(json, BackupData::class.java)

        db.withTransaction {
            db.clearAllTables()

            data.categories.orEmpty().forEach { db.categoryDao().upsert(it) }
            data.subcategories.orEmpty().forEach { db.subcategoryDao().upsert(it) }
            data.skills.orEmpty().forEach { db.skillDao().upsert(it) }
            data.habits.orEmpty().forEach { db.habitDao().upsert(it) }
            data.projects.orEmpty().forEach { db.projectDao().upsert(it) }
            data.milestones.orEmpty().forEach { db.milestoneDao().upsert(it) }
            data.tasks.orEmpty().forEach { db.taskDao().upsert(it) }
            data.subtasks.orEmpty().forEach { db.subtaskDao().upsert(it) }
            data.experiments.orEmpty().forEach { db.experimentDao().upsert(it) }
            data.transactions.orEmpty().forEach { db.transactionDao().upsert(it) }
            data.assets.orEmpty().forEach { db.assetDao().upsert(it) }
            data.learningSessions.orEmpty().forEach { db.learningDao().upsert(it) }
            data.habitLogs.orEmpty().forEach { db.habitDao().upsertLog(it) }
            data.notes.orEmpty().forEach { db.noteDao().upsert(it) }
            data.calendarEvents.orEmpty().forEach { db.calendarEventDao().upsert(it) }
            data.risks.orEmpty().forEach { db.riskDao().upsert(it) }
            data.settings.orEmpty().forEach { db.settingsDao().upsert(it) }
        }
    }
} 