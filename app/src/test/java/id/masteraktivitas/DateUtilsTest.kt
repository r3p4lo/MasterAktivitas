package id.masteraktivitas

import id.masteraktivitas.util.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.util.Calendar

class DateUtilsTest {

    @Test
    fun startOfDay_isMidnight() {
        val now = System.currentTimeMillis()
        val start = DateUtils.startOfDay(now)
        val cal = Calendar.getInstance()
        cal.timeInMillis = start

        assertEquals(0, cal.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, cal.get(Calendar.MINUTE))
        assertEquals(0, cal.get(Calendar.SECOND))
    }

    @Test
    fun streakCountsConsecutiveDays() {
        val today = LocalDate.now()
        val keys = listOf(
            today.toString(),
            today.minusDays(1).toString()
        )
        assertEquals(2, DateUtils.calculateStreak(keys))
    }

    @Test
    fun streakStartsYesterdayIfTodayNotDone() {
        val today = LocalDate.now()
        val keys = listOf(
            today.minusDays(1).toString(),
            today.minusDays(2).toString()
        )
        assertEquals(2, DateUtils.calculateStreak(keys))
    }
} 