package id.masteraktivitas.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase

@TypeConverters(Converters::class)
@Database(
    entities = [
        CategoryEntity::class,
        SubcategoryEntity::class,
        ProjectEntity::class,
        MilestoneEntity::class,
        TaskEntity::class,
        SubtaskEntity::class,
        ExperimentEntity::class,
        TransactionEntity::class,
        AssetEntity::class,
        LearningSessionEntity::class,
        SkillEntity::class,
        HabitEntity::class,
        HabitLogEntity::class,
        NoteEntity::class,
        CalendarEventEntity::class,
        RiskEntity::class,
        SettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun subcategoryDao(): SubcategoryDao
    abstract fun projectDao(): ProjectDao
    abstract fun milestoneDao(): MilestoneDao
    abstract fun taskDao(): TaskDao
    abstract fun subtaskDao(): SubtaskDao
    abstract fun experimentDao(): ExperimentDao
    abstract fun transactionDao(): TransactionDao
    abstract fun assetDao(): AssetDao
    abstract fun learningDao(): LearningDao
    abstract fun skillDao(): SkillDao
    abstract fun habitDao(): HabitDao
    abstract fun noteDao(): NoteDao
    abstract fun calendarEventDao(): CalendarEventDao
    abstract fun riskDao(): RiskDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "masteraktivitas.db"
                )
                    .addCallback(SeedDatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    class SeedDatabaseCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            db.execSQL(
                """
                INSERT INTO categories (id, name, icon, sortOrder, createdAt) VALUES
                (1, 'PENGHASILAN', '💰', 1, STRFTIME('%s','now') * 1000),
                (2, 'TEKNOLOGI', '🌐', 2, STRFTIME('%s','now') * 1000),
                (3, 'R&D', '🧪', 3, STRFTIME('%s','now') * 1000),
                (4, 'FINANCE', '💹', 4, STRFTIME('%s','now') * 1000),
                (5, 'KARIER', '🇯🇵', 5, STRFTIME('%s','now') * 1000),
                (6, 'PENDIDIKAN', '📚', 6, STRFTIME('%s','now') * 1000);
                """.trimIndent()
            )

            db.execSQL(
                """
                INSERT INTO subcategories (id, categoryId, name, sortOrder, createdAt) VALUES
                (1, 1, 'WINDAH CLIPER', 1, STRFTIME('%s','now') * 1000),
                (2, 1, 'Tembaga', 2, STRFTIME('%s','now') * 1000),
                (3, 1, 'Shopee', 3, STRFTIME('%s','now') * 1000),
                (4, 1, 'Airdrop Web3', 4, STRFTIME('%s','now') * 1000),
                (5, 1, 'Pertanian', 5, STRFTIME('%s','now') * 1000),
                (6, 2, 'Web3', 1, STRFTIME('%s','now') * 1000),
                (7, 2, 'Blockchain', 2, STRFTIME('%s','now') * 1000),
                (8, 2, 'AI', 3, STRFTIME('%s','now') * 1000),
                (9, 2, 'Automation', 4, STRFTIME('%s','now') * 1000),
                (10, 2, 'Termux', 5, STRFTIME('%s','now') * 1000),
                (11, 3, 'AI jualan WA', 1, STRFTIME('%s','now') * 1000),
                (12, 3, 'Clipper otomatis', 2, STRFTIME('%s','now') * 1000),
                (13, 3, 'Robot sampah', 3, STRFTIME('%s','now') * 1000),
                (14, 3, 'Sprinkler', 4, STRFTIME('%s','now') * 1000),
                (15, 3, 'Sistem tanaman', 5, STRFTIME('%s','now') * 1000),
                (16, 4, 'Trading', 1, STRFTIME('%s','now') * 1000),
                (17, 4, 'Mining', 2, STRFTIME('%s','now') * 1000),
                (18, 4, 'Wallet', 3, STRFTIME('%s','now') * 1000),
                (19, 4, 'Dana', 4, STRFTIME('%s','now') * 1000),
                (20, 5, 'Jepang', 1, STRFTIME('%s','now') * 1000),
                (21, 5, 'Bahasa Jepang pertanian', 2, STRFTIME('%s','now') * 1000),
                (22, 6, 'Tugas kejuruan', 1, STRFTIME('%s','now') * 1000),
                (23, 6, 'Inggris', 2, STRFTIME('%s','now') * 1000),
                (24, 6, 'Jerman', 3, STRFTIME('%s','now') * 1000);
                """.trimIndent()
            )

            db.execSQL(
                """
                INSERT INTO skills (id, name, category, level, progress, note, createdAt, updatedAt) VALUES
                (1, 'Japanese', 'KARIER', 'BEGINNER', 0, 'Default career language', STRFTIME('%s','now') * 1000, STRFTIME('%s','now') * 1000);
                """.trimIndent()
            )
        }
    }
} 