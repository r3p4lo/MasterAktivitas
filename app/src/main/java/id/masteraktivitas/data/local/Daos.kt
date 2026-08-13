package id.masteraktivitas.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: CategoryEntity): Long

    @Delete
    suspend fun delete(entity: CategoryEntity)

    @Query("SELECT * FROM categories ORDER BY sortOrder")
    fun observeAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories ORDER BY sortOrder")
    suspend fun getAll(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): CategoryEntity?
}

@Dao
interface SubcategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: SubcategoryEntity): Long

    @Delete
    suspend fun delete(entity: SubcategoryEntity)

    @Query("SELECT * FROM subcategories ORDER BY sortOrder")
    fun observeAll(): Flow<List<SubcategoryEntity>>

    @Query("SELECT * FROM subcategories ORDER BY sortOrder")
    suspend fun getAll(): List<SubcategoryEntity>
}

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ProjectEntity): Long

    @Delete
    suspend fun delete(entity: ProjectEntity)

    @Query("SELECT * FROM projects ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projects ORDER BY updatedAt DESC")
    suspend fun getAll(): List<ProjectEntity>

    @Query("SELECT COUNT(*) FROM projects p INNER JOIN categories c ON p.categoryId = c.id WHERE c.name = :categoryName AND p.status IN ('IDEA','PLANNED','IN_DEVELOPMENT','TESTING')")
    fun countActiveByCategory(categoryName: String): Flow<Int>
}

@Dao
interface MilestoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: MilestoneEntity): Long

    @Query("SELECT * FROM milestones WHERE projectId = :projectId ORDER BY sortOrder")
    fun observeByProject(projectId: Long): Flow<List<MilestoneEntity>>

    @Query("SELECT * FROM milestones ORDER BY id")
    suspend fun getAll(): List<MilestoneEntity>
}

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: TaskEntity): Long

    @Delete
    suspend fun delete(entity: TaskEntity)

    @Query("SELECT * FROM tasks ORDER BY CASE priority WHEN 'HIGH' THEN 0 WHEN 'MEDIUM' THEN 1 ELSE 2 END, deadline IS NULL, deadline ASC")
    fun observeAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY id")
    suspend fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE scheduledAt BETWEEN :start AND :end OR deadline BETWEEN :start AND :end")
    fun observeBetween(start: Long, end: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE scheduledAt BETWEEN :start AND :end OR deadline BETWEEN :start AND :end")
    suspend fun getBetween(start: Long, end: Long): List<TaskEntity>

    @Query("SELECT COUNT(*) FROM tasks WHERE status = :status")
    fun countByStatus(status: TaskStatus): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks t INNER JOIN categories c ON t.categoryId = c.id WHERE c.name = :categoryName AND t.status IN ('TODO','IN_PROGRESS','BLOCKED')")
    fun countActiveByCategory(categoryName: String): Flow<Int>

    @Query("SELECT * FROM tasks WHERE status != 'DONE' ORDER BY CASE priority WHEN 'HIGH' THEN 0 WHEN 'MEDIUM' THEN 1 ELSE 2 END, deadline IS NULL, deadline ASC LIMIT :limit")
    fun observeTopPriorities(limit: Int): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR notes LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<TaskEntity>
}

@Dao
interface SubtaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: SubtaskEntity): Long

    @Query("SELECT * FROM subtasks WHERE taskId = :taskId ORDER BY id")
    fun observeByTask(taskId: Long): Flow<List<SubtaskEntity>>

    @Query("SELECT * FROM subtasks ORDER BY id")
    suspend fun getAll(): List<SubtaskEntity>
}

@Dao
interface ExperimentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ExperimentEntity): Long

    @Query("SELECT * FROM experiments ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<ExperimentEntity>>

    @Query("SELECT * FROM experiments ORDER BY id")
    suspend fun getAll(): List<ExperimentEntity>
}

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: TransactionEntity): Long

    @Delete
    suspend fun delete(entity: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun observeBetween(start: Long, end: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    suspend fun getAll(): List<TransactionEntity>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type AND date BETWEEN :start AND :end")
    fun observeSumByTypeBetween(type: IncomeType, start: Long, end: Long): Flow<Long?>
}

@Dao
interface AssetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: AssetEntity): Long

    @Query("SELECT * FROM assets ORDER BY name")
    suspend fun getAll(): List<AssetEntity>
}

@Dao
interface LearningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: LearningSessionEntity): Long

    @Query("SELECT SUM(minutes) FROM learning_sessions WHERE date BETWEEN :start AND :end")
    fun observeTotalMinutesBetween(start: Long, end: Long): Flow<Int?>

    @Query("SELECT * FROM learning_sessions ORDER BY date DESC")
    suspend fun getAll(): List<LearningSessionEntity>
}

@Dao
interface SkillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: SkillEntity): Long

    @Query("SELECT MAX(progress) FROM skills WHERE name LIKE '%Japanese%' OR name LIKE '%Jepang%'")
    fun observeJapaneseProgress(): Flow<Int?>

    @Query("SELECT * FROM skills ORDER BY name")
    suspend fun getAll(): List<SkillEntity>
}

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: HabitEntity): Long

    @Query("SELECT * FROM habits WHERE active = 1 ORDER BY name")
    fun observeAll(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits ORDER BY name")
    suspend fun getAll(): List<HabitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLog(log: HabitLogEntity)

    @Query("SELECT * FROM habit_logs WHERE dateKey = :dateKey")
    suspend fun getLogsByDate(dateKey: String): List<HabitLogEntity>

    @Query("SELECT * FROM habit_logs ORDER BY id")
    suspend fun getAllLogs(): List<HabitLogEntity>

    @Query("SELECT dateKey FROM habit_logs WHERE habitId = :habitId AND done = 1 ORDER BY dateKey DESC")
    suspend fun getDoneDateKeys(habitId: Long): List<String>
}

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: NoteEntity): Long

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes ORDER BY id")
    suspend fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<NoteEntity>
}

@Dao
interface CalendarEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: CalendarEventEntity): Long

    @Query("SELECT * FROM calendar_events WHERE date BETWEEN :start AND :end ORDER BY date")
    fun observeBetween(start: Long, end: Long): Flow<List<CalendarEventEntity>>

    @Query("SELECT * FROM calendar_events ORDER BY date")
    suspend fun getAll(): List<CalendarEventEntity>
}

@Dao
interface RiskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: RiskEntity): Long

    @Query("SELECT * FROM risks WHERE projectId = :projectId ORDER BY id")
    fun observeByProject(projectId: Long): Flow<List<RiskEntity>>

    @Query("SELECT * FROM risks ORDER BY id")
    suspend fun getAll(): List<RiskEntity>
}

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: SettingsEntity): Long

    @Query("SELECT * FROM settings ORDER BY key")
    suspend fun getAll(): List<SettingsEntity>
} 