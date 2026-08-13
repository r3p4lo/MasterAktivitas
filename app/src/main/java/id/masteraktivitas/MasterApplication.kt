package id.masteraktivitas

import android.app.Application
import id.masteraktivitas.notifications.AlarmScheduler
import id.masteraktivitas.notifications.NotificationHelper

class MasterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
        NotificationHelper.createChannel(this)
        AlarmScheduler.scheduleDaily(this)
    }
} 