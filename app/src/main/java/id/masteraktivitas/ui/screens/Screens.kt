package id.masteraktivitas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import id.masteraktivitas.data.local.IncomeType
import id.masteraktivitas.data.local.Priority
import id.masteraktivitas.data.local.ProjectEntity
import id.masteraktivitas.data.local.ProjectStatus
import id.masteraktivitas.data.local.TaskEntity
import id.masteraktivitas.data.local.TransactionEntity
import id.masteraktivitas.domain.CalendarUiItem
import id.masteraktivitas.domain.HabitUi
import id.masteraktivitas.ui.components.ProgressBar
import id.masteraktivitas.ui.components.QuickAddTaskDialog
import id.masteraktivitas.ui.components.SectionCard
import id.masteraktivitas.ui.components.StatCard
import id.masteraktivitas.ui.components.toDuration
import id.masteraktivitas.ui.components.toRupiah
import id.masteraktivitas.ui.viewmodels.CalendarViewModel
import id.masteraktivitas.ui.viewmodels.DashboardViewModel
import id.masteraktivitas.ui.viewmodels.FinanceViewModel
import id.masteraktivitas.ui.viewmodels.MoreViewModel
import id.masteraktivitas.ui.viewmodels.ProjectsViewModel
import id.masteraktivitas.ui.viewmodels.TasksViewModel
import id.masteraktivitas.util.DateUtils
import id.masteraktivitas.util.label
import id.masteraktivitas.util.next

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val viewModel: DashboardViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "MASTER AKTIVITAS",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            SectionCard("TODAY") {
                Text("Progress Hari Ini")
                Spacer(Modifier.height(8.dp))
                ProgressBar(state.todayProgress)
                Spacer(Modifier.height(8.dp))
                Text("${state.todayProgress}%")
            }
        }

        item {
            Row(Modifier.fillMaxWidth()) {
                StatCard("Done", state.done.toString(), Modifier.weight(1f))
                StatCard("In Progress", state.inProgress.toString(), Modifier.weight(1f))
                StatCard("Blocked", state.blocked.toString(), Modifier.weight(1f))
            }
        }

        item {
            Row(Modifier.fillMaxWidth()) {
                StatCard("Income Today", state.incomeToday.toRupiah(), Modifier.weight(1f))
                StatCard("Learning", state.learningTodayMinutes.toDuration(), Modifier.weight(1f))
            }
        }

        item {
            Row(Modifier.fillMaxWidth()) {
                StatCard("R&D Active", state.rndActive.toString(), Modifier.weight(1f))
                StatCard("Tech Active", state.techActive.toString(), Modifier.weight(1f))
            }
        }

        item {
            SectionCard("CAREER") {
                Text("Japanese: ${state.japanProgress}%")
                Spacer(Modifier.height(8.dp))
                ProgressBar(state.japanProgress)
            }
        }

        item {
            SectionCard("TODAY'S PRIORITY") {
                if (state.priorities.isEmpty()) {
                    Text("Belum ada task aktif.")
                } else {
                    state.priorities.forEachIndexed { index, task ->
                        Text("${index + 1}. ${task.title} — ${task.priority.label()}")
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TasksScreen() {
    val viewModel: TasksViewModel = viewModel()
    val tasks by viewModel.tasks.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("TASK", style = MaterialTheme.typography.headlineSmall)
        }

        if (tasks.isEmpty()) {
            item { Text("Belum ada task. Gunakan FAB untuk menambah task.") }
        }

        items(tasks, key = { it.id }) { task ->
            TaskRow(
                task = task,
                onCycle = { viewModel.cycleStatus(task) },
                onDelete = { viewModel.delete(task) }
            )
        }
    }
}

@Composable
fun TaskRow(
    task: TaskEntity,
    onCycle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(4.dp))
                Text(
                    "${task.status.label()} • ${task.priority.label()}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "Deadline: ${DateUtils.formatDate(task.deadline ?: task.scheduledAt)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onCycle) {
                Icon(Icons.Filled.Refresh, contentDescription = "Cycle status")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun ProjectsScreen() {
    val viewModel: ProjectsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    var showAdd by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("PROJECT", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        Button(onClick = { showAdd = true }) {
            Text("Tambah Project")
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(state.projects, key = { it.id }) { project ->
                ProjectCard(project)
            }
        }
    }

    if (showAdd) {
        AddProjectDialog(
            categoryNames = state.categories.map { it.name },
            onDismiss = { showAdd = false },
            onAdd = { name, category, status, priority, progress, deadline ->
                viewModel.addProject(name, category, status, priority, progress, deadline)
                showAdd = false
            }
        )
    }
}

@Composable
fun ProjectCard(project: ProjectEntity) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(project.title, style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(6.dp))
            Text("Status: ${project.status.name}")
            Text("Priority: ${project.priority.label()}")
            Text("Deadline: ${DateUtils.formatDate(project.deadline)}")
            Spacer(Modifier.height(8.dp))
            ProgressBar(project.progress)
            Spacer(Modifier.height(4.dp))
            Text("${project.progress}%")
        }
    }
}

@Composable
fun AddProjectDialog(
    categoryNames: List<String>,
    onDismiss: () -> Unit,
    onAdd: (String, String, ProjectStatus, Priority, Int, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(ProjectStatus.IDEA) }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var progress by remember { mutableStateOf(0f) }
    var deadline by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Project Baru") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama project") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Kategori (misal: TEKNOLOGI)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = { status = status.next() }) {
                    Text("Status: ${status.name}")
                }
                TextButton(onClick = { priority = priority.next() }) {
                    Text("Priority: ${priority.label()}")
                }
                Spacer(Modifier.height(8.dp))
                Text("Progress: ${progress.toInt()}%")
                Slider(value = progress, onValueChange = { progress = it })
                OutlinedTextField(
                    value = deadline,
                    onValueChange = { deadline = it },
                    label = { Text("Deadline yyyy-MM-dd") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(6.dp))
                Text("Kategori tersedia: ${categoryNames.joinToString(", ")}", style = MaterialTheme.typography.bodySmall)
            }
        },
        confirmButton = {
            Button(
                enabled = name.isNotBlank(),
                onClick = {
                    onAdd(name, category, status, priority, progress.toInt(), deadline)
                }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}

@Composable
fun FinanceScreen() {
    val viewModel: FinanceViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    var showAdd by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("FINANCE", style = MaterialTheme.typography.headlineSmall)
        }

        item {
            SectionCard("MONTHLY SUMMARY") {
                Text("Income: ${state.income.toRupiah()}")
                Text("Expense: ${state.expense.toRupiah()}")
                Text("Net: ${state.net.toRupiah()}")
            }
        }

        item {
            Button(onClick = { showAdd = true }) {
                Text("Catat Transaksi")
            }
        }

        items(state.transactions, key = { it.id }) { tx ->
            TransactionRow(tx)
        }
    }

    if (showAdd) {
        AddTransactionDialog(
            onDismiss = { showAdd = false },
            onAdd = { source, type, amount, note ->
                viewModel.addTransaction(source, type, amount, note)
                showAdd = false
            }
        )
    }
}

@Composable
fun TransactionRow(tx: TransactionEntity) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(tx.sourceName, style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(4.dp))
            Text("${tx.type.name} • ${tx.amount.toRupiah()}")
            Text(DateUtils.formatDate(tx.date), style = MaterialTheme.typography.bodySmall)
            if (tx.note.isNotBlank()) {
                Text(tx.note, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onAdd: (String, IncomeType, Long, String) -> Unit
) {
    var source by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(IncomeType.INCOME) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Transaksi Baru") },
        text = {
            Column {
                OutlinedTextField(
                    value = source,
                    onValueChange = { source = it },
                    label = { Text("Sumber") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Jumlah") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Catatan") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = {
                    type = if (type == IncomeType.INCOME) IncomeType.EXPENSE else IncomeType.INCOME
                }) {
                    Text("Jenis: ${type.name}")
                }
            }
        },
        confirmButton = {
            Button(
                enabled = source.isNotBlank() && (amount.toLongOrNull() ?: 0L) > 0,
                onClick = {
                    onAdd(source, type, amount.toLongOrNull() ?: 0L, note)
                }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}

@Composable
fun MoreScreen(navController: NavController) {
    val viewModel: MoreViewModel = viewModel()
    val learningToday by viewModel.learningToday.collectAsState()
    val habits by viewModel.habits.collectAsState()
    val message by viewModel.message.collectAsState()

    var showLearningDialog by rememberSaveable { mutableStateOf(false) }
    var habitName by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("MORE", style = MaterialTheme.typography.headlineSmall)
        }

        item {
            SectionCard("LEARNING TRACKER") {
                Text("Learning today: ${learningToday.toDuration()}")
                Spacer(Modifier.height(8.dp))
                Button(onClick = { showLearningDialog = true }) {
                    Text("Catat Belajar")
                }
            }
        }

        item {
            SectionCard("HABIT TRACKER") {
                OutlinedTextField(
                    value = habitName,
                    onValueChange = { habitName = it },
                    label = { Text("Habit baru") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Button(onClick = {
                    viewModel.addHabit(habitName)
                    habitName = ""
                }) {
                    Text("Tambah Habit")
                }
                Spacer(Modifier.height(10.dp))

                if (habits.isEmpty()) {
                    Text("Belum ada habit.")
                }

                habits.forEach { item ->
                    HabitRow(item = item, onToggle = { viewModel.toggleHabit(item) })
                    Spacer(Modifier.height(6.dp))
                }
            }
        }

        item {
            SectionCard("BACKUP / RESTORE") {
                Button(onClick = { viewModel.exportBackup() }) {
                    Text("Export Data")
                }
                Spacer(Modifier.height(8.dp))
                Button(onClick = { viewModel.importBackup() }) {
                    Text("Import Data")
                }
                Spacer(Modifier.height(8.dp))
                if (message.isNotBlank()) {
                    Text(message, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        item {
            SectionCard("OTHER") {
                Button(onClick = { navController.navigate("calendar") }) {
                    Text("Buka Calendar")
                }
            }
        }
    }

    if (showLearningDialog) {
        AddLearningDialog(
            onDismiss = { showLearningDialog = false },
            onAdd = { topic, minutes ->
                viewModel.addLearning(topic, minutes)
                showLearningDialog = false
            }
        )
    }
}

@Composable
fun HabitRow(item: HabitUi, onToggle: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(10.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.doneToday,
                onCheckedChange = { onToggle() }
            )
            Column(Modifier.weight(1f)) {
                Text(item.habit.name)
                Text("Streak: ${item.streak}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun AddLearningDialog(
    onDismiss: () -> Unit,
    onAdd: (String, Int) -> Unit
) {
    var topic by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Catat Belajar") },
        text = {
            Column {
                OutlinedTextField(
                    value = topic,
                    onValueChange = { topic = it },
                    label = { Text("Topik") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = minutes,
                    onValueChange = { minutes = it },
                    label = { Text("Menit") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                enabled = topic.isNotBlank() && (minutes.toIntOrNull() ?: 0) > 0,
                onClick = { onAdd(topic, minutes.toIntOrNull() ?: 0) }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}

@Composable
fun CalendarScreen() {
    val viewModel: CalendarViewModel = viewModel()
    val items by viewModel.items.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("CALENDAR", style = MaterialTheme.typography.headlineSmall)
        }

        if (items.isEmpty()) {
            item { Text("Belum ada agenda 30 hari ke depan.") }
        }

        items(items, key = { "${it.date}-${it.title}" }) { item ->
            CalendarRow(item)
        }
    }
}

@Composable
fun CalendarRow(item: CalendarUiItem) {
    val color = when {
        item.type.contains("TASK") -> Color(0xFF60A5FA)
        item.type.contains("DEADLINE") -> Color(0xFFF87171)
        item.type.contains("MILESTONE") -> Color(0xFF34D399)
        item.type.contains("STUDY") -> Color(0xFFFBBF24)
        else -> Color(0xFF9CA3AF)
    }

    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp)) {
            Surface(
                modifier = Modifier
                    .width(6.dp)
                    .height(48.dp),
                color = color
            ) {}
            Spacer(Modifier.width(10.dp))
            Column {
                Text(item.title, style = MaterialTheme.typography.titleSmall)
                Text(DateUtils.formatDate(item.date), style = MaterialTheme.typography.bodySmall)
                Text("${item.type} ${item.status}".trim(), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
} 