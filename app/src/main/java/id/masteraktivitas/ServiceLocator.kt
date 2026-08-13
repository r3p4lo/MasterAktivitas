package id.masteraktivitas

import android.content.Context
import id.masteraktivitas.data.backup.BackupRepository
import id.masteraktivitas.data.local.AppDatabase
import id.masteraktivitas.data.repository.AppRepository

object ServiceLocator {
    lateinit var database: AppDatabase
    lateinit var repository: AppRepository
    lateinit var backupRepository: BackupRepository

    fun init(context: Context) {
        if (!::database.isInitialized) {
            database = AppDatabase.get(context)
            repository = AppRepository(database)
            backupRepository = BackupRepository(context, database)
        }
    }
} 