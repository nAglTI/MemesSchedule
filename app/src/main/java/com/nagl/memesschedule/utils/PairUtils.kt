package com.nagl.memesschedule.utils

class PairUtils {
    companion object {
        @JvmStatic
        fun getCabinetString(classNumber: String): String {
            return "Кабинет: ${classNumber.trim()}"
        }
        @JvmStatic
        fun getTeacherName(teacher: String): String {
            return teacher.trim()
        }
        @JvmStatic
        fun getPairTime(pairNumber: Int): String {
            return "Время: " + when (pairNumber) {
                1 -> "9:00 - 10:30"
                2 -> "10:40 - 12:10"
                3 -> "12:50 - 14:20"
                4 -> "14:30 - 16:00"
                5 -> "16:10 - 17:40"
                6 -> "17:50 - 19:20"
                else -> "No time to die!"
            }
        }

        @JvmStatic
        fun pairNumberToString(pairNumber: Int): String = pairNumber.toString()

        @JvmStatic
        fun getDayOfWeekByNumber(dayNumber: Int): String {
            return when (dayNumber) {
                1 -> "ПН"
                2 -> "ВТ"
                3 -> "СР"
                4 -> "ЧТ"
                5 -> "ПТ"
                6 -> "СБ"
                else -> "ПН"
            }
        }
    }
}