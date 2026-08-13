package id.masteraktivitas.domain

import id.masteraktivitas.data.local.CategoryEntity
import id.masteraktivitas.data.local.HabitEntity
import id.masteraktivitas.data.local.ProjectEntity
import id.masteraktivitas.data.local.TaskEntity
import id.masteraktivitas.data.local.TransactionEntity

data class DashboardUiState(
    val todayProgress: Int = 0,
    val done: Int = 0,
    val inProgress: Int = 0,
    val blocked: Int = 0,
    val incomeToday: Long = 0L,
    val learningTodayMinutes: Int = 0,
    val rndActive: Int = 0,
    val techActive: Int = 0,
    val japanProgress: Int = 0,
    val priorities: List<TaskEntity> = emptyList()
)

data class ProjectsUiState(
    val projects: List<ProjectEntity> = emptyList(),
    val categories: List<CategoryEntity> = emptyList()
)

data class FinanceUiState(
    val income: Long = 0L,
    val expense: Long = 0L,
    val net: Long = 0L,
    val transactions: List<TransactionEntity> = emptyList()
)

data class HabitUi(
    val habit: HabitEntity,
    val doneToday: Boolean,
    val streak: Int
)

data class CalendarUiItem(
    val date: Long,
    val title: String,
    val type: String,
    val status: String = ""
) 