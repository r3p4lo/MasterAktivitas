package id.masteraktivitas.data.repository

import id.masteraktivitas.data.local.*
import id.masteraktivitas.domain.CalendarUiItem
import id.masteraktivitas.domain.DashboardUiState
import id.masteraktivitas.domain.FinanceUiState
import id.masteraktivitas.util.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class AppRepository(private val db: AppDatabase) {

    private val categoryDao = db.categoryDao()
    private val projectDao = db.projectDao()
    private val taskDao = db.taskDao()
    private val transactionDao = db.transactionDao()
    private val learningDao = db.learningDao()
    private val skillDao = db.skillDao()
    private val habitDao = db.habitDao()
    private val calendarEventDao = db.calendarEventDao()

    fun observeCategories(): Flow<List<CategoryEntity>> = categoryDao.observeAll()

    suspend fun findCategoryByName(name: String): CategoryEntity? {
        return categoryDao.getByName(name.trim())
    }

    fun observeTasks(): Flow<List<TaskEntity>> = taskDao.observeAll()

    suspend fun upsertTask(task: TaskEntity): Long = taskDao.upsert(task)

    suspend fun deleteTask(task: TaskEntity) = taskDao.delete(task)

    suspend fun getTasksBetweenOnce(start: Long, end: Long): List<TaskEntity> {
        return taskDao.getBetween(start, end)
    }

    fun observeProjects(): Flow<List<ProjectEntity>> = projectDao.observeAll()

    suspend fun upsertProject(project: ProjectEntity): Long = projectDao.upsert(project)

    suspend fun deleteProject(project: ProjectEntity) = projectDao.delete(project)

    suspend fun upsertTransaction(entity: TransactionEntity): Long = transactionDao.upsert(entity)

    fun observeLearningToday(): Flow<Int> {
        val start = DateUtils.startOfDay()
        val end = DateUtils.endOfDay()
        return learningDao.observeTotalMinutesBetween(start, end).map { it ?: 0 }
    }

    suspend fun addLearningSession(topic: String, minutes: Int) {
        learningDao.upsert(
            LearningSessionEntity(
                topic = topic,
                minutes = minutes,
                date = System.currentTimeMillis()
            )
        )
    }

    suspend fun getHabits(): List<HabitEntity> = habitDao.getAll()

    suspend fun getTodayLogs(): List<HabitLogEntity> = habitDao.getLogsByDate(DateUtils.todayKey())

    suspend fun toggleHabit(habitId: Long, done: Boolean) {
        habitDao.upsertLog(
            HabitLogEntity(
                habitId = habitId,
                dateKey = DateUtils.todayKey(),
                done = done
            )
        )
    }

    suspend fun calculateStreak(habitId: Long): Int {
        return DateUtils.calculateStreak(habitDao.getDoneDateKeys(habitId))
    }

    suspend fun upsertHabit(name: String) {
        habitDao.upsert(HabitEntity(name = name))
    }

    @Suppress("UNCHECKED_CAST")
    fun observeDashboard(): Flow<DashboardUiState> {
        val start = DateUtils.startOfDay()
        val end = DateUtils.endOfDay()

        val f1: Flow<Any?> = taskDao.observeBetween(start, end).map { it as Any? }
        val f2: Flow<Any?> = taskDao.countByStatus(TaskStatus.DONE).map { it as Any? }
        val f3: Flow<Any?> = taskDao.countByStatus(TaskStatus.IN_PROGRESS).map { it as Any? }
        val f4: Flow<Any?> = taskDao.countByStatus(TaskStatus.BLOCKED).map { it as Any? }
        val f5: Flow<Any?> = transactionDao.observeSumByTypeBetween(IncomeType.INCOME, start, end).map { it as Any? }
        val f6: Flow<Any?> = learningDao.observeTotalMinutesBetween(start, end).map { it as Any? }
        val f7: Flow<Any?> = projectDao.countActiveByCategory("R&D").map { it as Any? }
        val f8: Flow<Any?> = taskDao.countActiveByCategory("TEKNOLOGI").map { it as Any? }
        val f9: Flow<Any?> = skillDao.observeJapaneseProgress().map { it as Any? }
        val f10: Flow<Any?> = taskDao.observeTopPriorities(5).map { it as Any? }

        return combine(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10) { args ->
            val todayTasks = (args[0] as? List<*>)?.filterIsInstance<TaskEntity>().orEmpty()
            val done = (args[1] as? Int) ?: 0
            val inProgress = (args[2] as? Int) ?: 0
            val blocked = (args[3] as? Int) ?: 0
            val incomeToday = (args[4] as? Long) ?: 0L
            val learningToday = (args[5] as? Int) ?: 0
            val rndActive = (args[6] as? Int) ?: 0
            val techActive = (args[7] as? Int) ?: 0
            val japanProgress = (args[8] as? Int) ?: 0
            val priorities = (args[9] as? List<*>)?.filterIsInstance<TaskEntity>().orEmpty()

            val totalToday = todayTasks.size
            val doneToday = todayTasks.count { it.status == TaskStatus.DONE }
            val todayProgress = if (totalToday == 0) 0 else (doneToday * 100) / totalToday

            DashboardUiState(
                todayProgress = todayProgress,
                done = done,
                inProgress = inProgress,
                blocked = blocked,
                incomeToday = incomeToday,
                learningTodayMinutes = learningToday,
                rndActive = rndActive,
                techActive = techActive,
                japanProgress = japanProgress,
                priorities = priorities
            )
        }
    }

    fun observeFinanceMonth(): Flow<FinanceUiState> {
        val start = DateUtils.startOfMonth()
        val end = DateUtils.endOfMonth()

        return combine(
            transactionDao.observeSumByTypeBetween(IncomeType.INCOME, start, end),
            transactionDao.observeSumByTypeBetween(IncomeType.EXPENSE, start, end),
            transactionDao.observeBetween(start, end)
        ) { income, expense, transactions ->
            val inc = income ?: 0L
            val exp = expense ?: 0L
            FinanceUiState(
                income = inc,
                expense = exp,
                net = inc - exp,
                transactions = transactions
            )
        }
    }

    fun observeUpcomingCalendar(days: Int): Flow<List<CalendarUiItem>> {
        val start = System.currentTimeMillis()
        val end = start + days * 24L * 60 * 60 * 1000

        return combine(
            taskDao.observeBetween(start, end),
            calendarEventDao.observeBetween(start, end)
        ) { tasks, events ->
            val taskItems = tasks.map { task ->
                CalendarUiItem(
                    date = task.deadline ?: task.scheduledAt ?: task.createdAt,
                    title = task.title,
                    type = "TASK",
                    status = task.status.name
                )
            }

            val eventItems = events.map { event ->
                CalendarUiItem(
                    date = event.date,
                    title = event.title,
                    type = event.type.name,
                    status = ""
                )
            }

            (taskItems + eventItems).sortedBy { it.date }
        }
    }
} 