package com.shegor.samplenewsapp.utils

import android.content.Context
import com.shegor.samplenewsapp.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {

    fun jsonDateToLocalDate(jsonDate: String?): LocalDateTime {

        val jsonDateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return LocalDateTime.parse(jsonDate, jsonDateFormatter)
    }

    fun jsonDateToFormattedDate(dateString: String?, context: Context): String {

        val currentDate = LocalDateTime.now()
        val date = jsonDateToLocalDate(dateString)

        val fullDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        val daysMonthsDateFormatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm")
        val timeDateFormatter = DateTimeFormatter.ofPattern("HH:mm")


        return when {
            (currentDate.year - date.year) >= 1 -> date.format(fullDateFormatter)
            (currentDate.dayOfMonth - date.dayOfMonth) > 1 -> date.format(daysMonthsDateFormatter)
            (currentDate.dayOfMonth - date.dayOfMonth) == 1 -> context.getString(
                R.string.date_formatting_yesterday,
                date.format(timeDateFormatter)
            )
            (currentDate.hour - date.hour) >= 1 -> context.getString(
                R.string.date_formatting_today,
                date.format(timeDateFormatter)
            )
            (currentDate.minute - date.minute) > 1 -> {
                if (date.minute == 11) return context.getString(
                    R.string.date_formatting_minutes_from_5_to_9_0_11,
                    date.minute
                )
                when (date.minute % 10) {
                    in 2..4 -> context.getString(
                        R.string.date_formatting_minutes_from_2_to_4,
                        date.minute
                    )
                    in 5..9, 0 -> context.getString(
                        R.string.date_formatting_minutes_from_5_to_9_0_11,
                        date.minute
                    )
                    1 -> context.getString(R.string.date_formatting_minutes_1, date.minute)
                    else -> ""
                }
            }
            (currentDate.minute - date.minute) <= 1 -> context.getString(R.string.date_formatting_recently)
            else -> ""
        }
    }
}