package com.nagl.memesschedule.utils

import com.nagl.memesschedule.data.model.Schedule
import com.nagl.memesschedule.data.model.UniPair
import org.joda.time.DateTime
import org.joda.time.DateTimeUtils
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class ScheduleUtilsTest {

    private fun makeSchedule(oddLastDay: Int = 5, evenLastDay: Int = 6): Schedule {
        val odd = listOf(UniPair(oddLastDay, 1, "", "", ""))
        val even = listOf(UniPair(evenLastDay, 1, "", "", ""))
        return Schedule(false, even, odd)
    }

    @After
    fun tearDown() {
        DateTimeUtils.setCurrentMillisSystem()
        isNextWeek = false
    }

    @Test
    fun getWeeksBetween_returnsCorrectDifference() {
        DateTimeUtils.setCurrentMillisFixed(DateTime(2023, 9, 15, 0, 0).millis)
        assertEquals(2, getWeeksBetween())
    }

    @Test
    fun isOddWeek_considersCurrentWeekAndEndOfWeek() {
        DateTimeUtils.setCurrentMillisFixed(DateTime(2023, 9, 1, 0, 0).millis)
        val schedule = makeSchedule()
        assertEquals(true, isOddWeek(schedule))

        DateTimeUtils.setCurrentMillisFixed(DateTime(2023, 9, 3, 0, 0).millis)
        assertEquals(false, isOddWeek(schedule))
    }

    @Test
    fun getDayOfWeekDate_returnsDateWithNextWeekFlag() {
        DateTimeUtils.setCurrentMillisFixed(DateTime(2023, 9, 4, 0, 0).millis)
        val date = getDayOfWeekDate(3)
        assertEquals("06.09.2023", date)

        isNextWeek = true
        val nextWeekDate = getDayOfWeekDate(3)
        assertEquals("13.09.2023", nextWeekDate)
    }
}
