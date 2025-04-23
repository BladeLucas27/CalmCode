package com.example.calmcode.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun updateStreakCounter(context: Context) {
    val prefs = context.getSharedPreferences("StreakPrefs", Context.MODE_PRIVATE)
    val lastDateStr = prefs.getString("lastListenDate", null)
    val streakCount = prefs.getInt("streakCount", 0)

    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    if (lastDateStr == null) {
        prefs.edit().apply {
            putString("lastListenDate", today.format(formatter))
            putInt("streakCount", 1)
            apply()
        }
        return
    }

    val lastDate = LocalDate.parse(lastDateStr, formatter)
    val daysBetween = ChronoUnit.DAYS.between(lastDate, today)

    when {
        daysBetween == 0L -> {
            // already updated today, no changes
            return
        }
        daysBetween == 1L -> {
            // streak continues
            prefs.edit().apply {
                putString("lastListenDate", today.format(formatter))
                putInt("streakCount", streakCount + 1)
                apply()
            }
        }
        daysBetween > 1L -> {
            // user missed a day, streak broken
            prefs.edit().apply {
                putString("lastListenDate", today.format(formatter))
                putInt("streakCount", 1) // starting a new streak from today
                apply()
            }
        }
    }
}

