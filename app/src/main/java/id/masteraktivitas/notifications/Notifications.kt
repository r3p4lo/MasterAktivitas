package id.masteraktivitas.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import id.masteraktivitas.ServiceLocator
import id.masteraktivitas.data.local.TaskStatus
import id.masteraktivitas.util.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

object NotificationHelper {
    private const val CHANNEL_ID = "master_reminder"
    private const val CHANNEL_NAME = "Master Aktivitas Reminder"

    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun showDailyReminder(context: Context, taskTitles: List<String>) {
        try {
            val title = "MASTER AKTIVITAS"
            val text = if (taskTitles.size == 1) {
                "Task deadline hari ini: ${taskTitles.first()}"
            } else {
                "${taskTitles.size} task perlu diperhatikan hari ini"
            }

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(NotificationCompat.BigTextStyle().bigText(taskTitles.joinToString("\n")))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context).notify(2001, notification)
        } catch (e: SecurityException) {
            // Notification permission belum diberikan
        }
    }
}

class DailyReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val start = DateUtils.startOfDay()
                val end = DateUtils.endOfDay()
                val repo = ServiceLocator.repository
                val tasks = repo.getTasksBetweenOnce(start, end)
                    .filter { it.status != TaskStatus.DONE }
                    .map { it.title }

                if (tasks.isNotEmpty()) {
                    NotificationHelper.showDailyReminder(context, tasks)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}

object AlarmScheduler {
    fun scheduleDaily(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1001,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
} 