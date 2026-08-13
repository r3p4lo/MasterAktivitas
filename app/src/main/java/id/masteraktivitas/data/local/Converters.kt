package id.masteraktivitas.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun priorityToString(value: Priority): String = value.name

    @TypeConverter
    fun stringToPriority(value: String): Priority = Priority.valueOf(value)

    @TypeConverter
    fun taskStatusToString(value: TaskStatus): String = value.name

    @TypeConverter
    fun stringToTaskStatus(value: String): TaskStatus = TaskStatus.valueOf(value)

    @TypeConverter
    fun projectStatusToString(value: ProjectStatus): String = value.name

    @TypeConverter
    fun stringToProjectStatus(value: String): ProjectStatus = ProjectStatus.valueOf(value)

    @TypeConverter
    fun incomeTypeToString(value: IncomeType): String = value.name

    @TypeConverter
    fun stringToIncomeType(value: String): IncomeType = IncomeType.valueOf(value)

    @TypeConverter
    fun experimentStatusToString(value: ExperimentStatus): String = value.name

    @TypeConverter
    fun stringToExperimentStatus(value: String): ExperimentStatus = ExperimentStatus.valueOf(value)

    @TypeConverter
    fun skillLevelToString(value: SkillLevel): String = value.name

    @TypeConverter
    fun stringToSkillLevel(value: String): SkillLevel = SkillLevel.valueOf(value)

    @TypeConverter
    fun riskImpactToString(value: RiskImpact): String = value.name

    @TypeConverter
    fun stringToRiskImpact(value: String): RiskImpact = RiskImpact.valueOf(value)

    @TypeConverter
    fun riskTypeToString(value: RiskType): String = value.name

    @TypeConverter
    fun stringToRiskType(value: String): RiskType = RiskType.valueOf(value)

    @TypeConverter
    fun eventTypeToString(value: EventType): String = value.name

    @TypeConverter
    fun stringToEventType(value: String): EventType = EventType.valueOf(value)
} 