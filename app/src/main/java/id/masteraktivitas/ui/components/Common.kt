package id.masteraktivitas.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.masteraktivitas.ServiceLocator
import id.masteraktivitas.data.local.Priority
import id.masteraktivitas.data.local.TaskEntity
import id.masteraktivitas.data.local.TaskStatus
import id.masteraktivitas.util.label
import id.masteraktivitas.util.next
import kotlinx.coroutines.launch
import java.util.Locale

fun Long.toRupiah(): String {
    return "Rp " + String.format(Locale("id_ID"), "%,d", this)
}

fun Int.toDuration(): String {
    val h = this / 60
    val m = this % 60
    return if (h > 0) "${h}h ${m}m" else "${m}m"
}

@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
fun ProgressBar(progress: Int, modifier: Modifier = Modifier) {
    val normalized = progress.coerceIn(0, 100) / 100f
    LinearProgressIndicator(
        progress = { normalized },
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}

@Composable
fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(text = label, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun QuickAddTaskDialog(onDismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var status by remember { mutableStateOf(TaskStatus.TODO) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Task Baru") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Nama task") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Deskripsi") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                TextButton(onClick = { priority = priority.next() }) {
                    Text("Priority: ${priority.label()}")
                }

                TextButton(onClick = { status = status.next() }) {
                    Text("Status: ${status.label()}")
                }
            }
        },
        confirmButton = {
            Button(
                enabled = title.isNotBlank(),
                onClick = {
                    scope.launch {
                        ServiceLocator.repository.upsertTask(
                            TaskEntity(
                                title = title.trim(),
                                description = description.trim(),
                                priority = priority,
                                status = status
                            )
                        )
                        onDismiss()
                    }
                }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
} 