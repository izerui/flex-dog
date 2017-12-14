package com.github.izerui.file.utils

import java.util.Date

object RelativeDateFormat {

    private val ONE_MINUTE = 60000L
    private val ONE_HOUR = 3600000L
    private val ONE_DAY = 86400000L
    private val ONE_WEEK = 604800000L

    private val ONE_SECOND_AGO = "秒前"
    private val ONE_MINUTE_AGO = "分钟前"
    private val ONE_HOUR_AGO = "小时前"
    private val ONE_DAY_AGO = "天前"
    private val ONE_MONTH_AGO = "月前"
    private val ONE_YEAR_AGO = "年前"

    fun format(date: Date): String {
        val delta = Date().time - date.time
        when {
            (delta < 45L * ONE_MINUTE) -> {
                val minutes = toMinutes(delta)
                return (if (minutes <= 0) 1 else minutes).toString() + ONE_MINUTE_AGO
            }
            (delta < 24L * ONE_HOUR) -> {
                val hours = toHours(delta)
                return (if (hours <= 0) 1 else hours).toString() + ONE_HOUR_AGO
            }
            (delta < 48L * ONE_HOUR) -> {
                return "昨天"
            }
            (delta < 30L * ONE_DAY) -> {
                val days = toDays(delta)
                return (if (days <= 0) 1 else days).toString() + ONE_DAY_AGO
            }
            (delta < 12L * 4L * ONE_WEEK) -> {
                val months = toMonths(delta)
                return (if (months <= 0) 1 else months).toString() + ONE_MONTH_AGO
            }
            else ->{
                val years = toYears(delta)
                return (if (years <= 0) 1 else years).toString() + ONE_YEAR_AGO
            }

        }
    }

    private fun toSeconds(date: Long): Long {
        return date / 1000L
    }

    private fun toMinutes(date: Long): Long {
        return toSeconds(date) / 60L
    }

    private fun toHours(date: Long): Long {
        return toMinutes(date) / 60L
    }

    private fun toDays(date: Long): Long {
        return toHours(date) / 24L
    }

    private fun toMonths(date: Long): Long {
        return toDays(date) / 30L
    }

    private fun toYears(date: Long): Long {
        return toMonths(date) / 365L
    }

}  