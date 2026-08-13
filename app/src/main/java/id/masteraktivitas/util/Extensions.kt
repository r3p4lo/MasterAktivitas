package id.masteraktivitas.util

import id.masteraktivitas.data.local.Priority
import id.masteraktivitas.data.local.ProjectStatus
import id.masteraktivitas.data.local.TaskStatus

fun TaskStatus.next(): TaskStatus {
    return when (this) {
        TaskStatus.TODO -> TaskStatus.IN_PROGRESS
        TaskStatus.IN_PROGRESS -> TaskStatus.BLOCKED
        TaskStatus.BLOCKED -> TaskStatus.DONE
        TaskStatus.DONE -> TaskStatus.TODO
    }
}

fun ProjectStatus.next(): ProjectStatus {
    return when (this) {
        ProjectStatus.IDEA -> ProjectStatus.PLANNED
        ProjectStatus.PLANNED -> ProjectStatus.IN_DEVELOPMENT
        ProjectStatus.IN_DEVELOPMENT -> ProjectStatus.TESTING
        ProjectStatus.TESTING -> ProjectStatus.DONE
        ProjectStatus.DONE -> ProjectStatus.BLOCKED
        ProjectStatus.BLOCKED -> ProjectStatus.PAUSED
        ProjectStatus.PAUSED -> ProjectStatus.IDEA
    }
}

fun Priority.next(): Priority {
    return when (this) {
        Priority.HIGH -> Priority.MEDIUM
        Priority.MEDIUM -> Priority.LOW
        Priority.LOW -> Priority.HIGH
    }
}

fun Priority.label(): String {
    return when (this) {
        Priority.HIGH -> "🔴 HIGH"
        Priority.MEDIUM -> "🟡 MEDIUM"
        Priority.LOW -> "🟢 LOW"
    }
}

fun TaskStatus.label(): String {
    return when (this) {
        TaskStatus.TODO -> "TODO"
        TaskStatus.IN_PROGRESS -> "IN PROGRESS"
        TaskStatus.BLOCKED -> "BLOCKED"
        TaskStatus.DONE -> "DONE"
    }
} 