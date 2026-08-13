package id.masteraktivitas.util

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

object DateUtils {

    fun startOfDay(time: Long = System.currentTimeMillis()): Long {
        return Calendar.getInstance().apply {
            timeInMillis = time
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    fun endOfDay(time: Long = System.currentTimeMillis()): Long {
        return startOfDay(time) + 24 * 60 * 60 * 1000L - 1
    }

    fun startOfMonth(time: Long = System.currentTimeMillis()): Long {
        return Calendar.getInstance().apply {
            timeInMillis = time
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    fun endOfMonth(time: Long = System.currentTimeMillis()): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = startOfMonth(time)
        cal.add(Calendar.MONTH, 1)
        return cal.timeInMillis - 1
    }

    fun todayKey(): String {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    fun calculateStreak(dateKeys: List<String>): Int {
        val set = dateKeys.toSet()
        var date = LocalDate.now()

        if (!set.contains(date.toString)) {
            date = date.minusDays(1)
        }

        var streak = 0
        while (set.contains(date.toString)) {
            streak++
            date = date.minusDays(1)
        }
        return streak
    }

    fun formatDate(timestamp: Long?): String {
        return try {
            timestamp?.let {
                val sdf = java.text.SimpleDateFormat("dd MMM yyyy HH:mm", java.util.Locale.getDefault())
                sdf.format(java.util.Date(it))
            } ?: "-"
        } catch (e: Exception) {
            "-"
        }
    }

    fun parseDate(input: String): Long? {
        return try {
            if (input.isBlank()) return null
            val date = LocalDate.parse(input.trim())
            date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } catch (e: Exception) {
            null
        }
    }
} 