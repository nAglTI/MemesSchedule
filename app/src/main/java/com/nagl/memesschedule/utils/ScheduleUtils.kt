package com.nagl.memesschedule.utils

import com.nagl.memesschedule.data.model.Schedule
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.util.Calendar
import java.util.Date

var isNextWeek = false

fun isOddWeek(schedule: Schedule): Boolean {
    return when (getWeeksBetween() % 2 == 0) {
        true -> {
            !isEndOfScheduleWeek(schedule.oddWeek.last().dayNumber)
        }
        false -> {
            isEndOfScheduleWeek(schedule.evenWeek.last().dayNumber)
        }
    }
}

fun getWeeksBetween(): Int {
    val cal1 = LocalDate(LocalDate.now().year, 9, 1)
    val calCurr = LocalDate.now()

    return calCurr.weekOfWeekyear - cal1.weekOfWeekyear
}

fun isEndOfScheduleWeek(lastDayNumber: Int): Boolean {
    val dayNum = LocalDate.now().dayOfWeek
    isNextWeek = dayNum > lastDayNumber
    return isNextWeek
}