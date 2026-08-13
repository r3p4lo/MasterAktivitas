package id.masteraktivitas.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.masteraktivitas.ServiceLocator
import id.masteraktivitas.data.local.IncomeType
import id.masteraktivitas.data.local.Priority
import id.masteraktivitas.data.local.ProjectEntity
import id.masteraktivitas.data.local.ProjectStatus
import id.masteraktivitas.data.local.TaskEntity
import id.masteraktivitas.data.local.TaskStatus
import id.masteraktivitas.data.local.TransactionEntity
import id.masteraktivitas.domain.DashboardUiState
import id.masteraktivitas.domain.FinanceUiState
import id.masteraktivitas.domain.HabitUi
import id.masteraktivitas.domain.ProjectsUiState
import id.masteraktivitas.util.DateUtils
import id.masteraktivitas.util.next
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val repo = ServiceLocator.repository
    val uiState: StateFlow<DashboardUiState> = repo.observeDashboard()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardUiState())
}

class TasksViewModel : ViewModel() {
    private val repo = ServiceLocator.repository

    val tasks = repo.observeTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun cycleStatus(task: TaskEntity) {
        val nextStatus = task.status.next()
        viewModelScope.launch {
            repo.upsertTask(
                task.copy(
                    status = nextStatus,
                    updatedAt = System.currentTimeMillis(),
                    completedAt = if (nextStatus == TaskStatus.DONE) System.currentTimeMillis() else null,
                    progress = if (nextStatus == TaskStatus.DONE) 100 else task.progress
                )
            )
        }
    }

    fun delete(task: TaskEntity) {
        viewModelScope.launch { repo.deleteTask(task) }
    }
}

class ProjectsViewModel : ViewModel() {
    private val repo = ServiceLocator.repository

    val uiState = combine(
        repo.observeProjects(),
        repo.observeCategories()
    ) { projects, categories ->
        ProjectsUiState(projects = projects, categories = categories)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProjectsUiState())

    fun addProject(
        name: String,
        categoryName: String,
        status: ProjectStatus,
        priority: Priority,
        progress: Int,
        deadlineText: String
    ) {
        viewModelScope.launch {
            val categoryId = if (categoryName.isBlank()) {
                null
            } else {
                repo.findCategoryByName(categoryName)?.id
            }

            repo.upsertProject(
                ProjectEntity(
                    title = name.trim(),
                    categoryId = categoryId,
                    status = status,
                    priority = priority,
                    progress = progress,
                    deadline = DateUtils.parseDate(deadlineText),
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun delete(project: ProjectEntity) {
        viewModelScope.launch { repo.deleteProject(project) }
    }
}

class FinanceViewModel : ViewModel() {
    private val repo = ServiceLocator.repository

    val uiState: StateFlow<FinanceUiState> = repo.observeFinanceMonth()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FinanceUiState())

    fun addTransaction(source: String, type: IncomeType, amount: Long, note: String) {
        if (amount <= 0) return
        viewModelScope.launch {
            repo.upsertTransaction(
                TransactionEntity(
                    sourceName = source.ifBlank { "Lainnya" },
                    type = type,
                    amount = amount,
                    date = System.currentTimeMillis(),
                    note = note
                )
            )
        }
    }
}

class MoreViewModel : ViewModel() {
    private val repo = ServiceLocator.repository

    val learningToday = repo.observeLearningToday()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _habits = MutableStateFlow<List<HabitUi>>(emptyList())
    val habits = _habits.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    init {
        refreshHabits()
    }

    fun refreshHabits() {
        viewModelScope.launch {
            val habits = repo.getHabits()
            val logs = repo.getTodayLogs()
            _habits.value = habits.map { habit ->
                HabitUi(
                    habit = habit,
                    doneToday = logs.any { it.habitId == habit.id && it.done },
                    streak = repo.calculateStreak(habit.id)
                )
            }
        }
    }

    fun toggleHabit(item: HabitUi) {
        viewModelScope.launch {
            repo.toggleHabit(item.habit.id, !item.doneToday)
            refreshHabits()
        }
    }

    fun addHabit(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repo.upsertHabit(name.trim())
            refreshHabits()
        }
    }

    fun addLearning(topic: String, minutes: Int) {
        if (topic.isBlank() || minutes <= 0) return
        viewModelScope.launch {
            repo.addLearningSession(topic.trim(), minutes)
            _message.value = "Learning dicatat"
        }
    }

    fun exportBackup() {
        viewModelScope.launch {
            try {
                val file = ServiceLocator.backupRepository.exportJson()
                _message.value = "Backup tersimpan: ${file.absolutePath}"
            } catch (e: Exception) {
                _message.value = "Backup gagal: ${e.message}"
            }
        }
    }

    fun importBackup() {
        viewModelScope.launch {
            try {
                ServiceLocator.backupRepository.importDefault()
                _message.value = "Restore selesai"
                refreshHabits()
            } catch (e: Exception) {
                _message.value = "Restore gagal: ${e.message}"
            }
        }
    }
}

class CalendarViewModel : ViewModel() {
    private val repo = ServiceLocator.repository
    val items = repo.observeUpcomingCalendar(30)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
} 