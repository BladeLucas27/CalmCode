package com.example.calmcode

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.calmcode.utils.ReminderReceiver
import java.util.Calendar
import android.app.TimePickerDialog
import androidx.appcompat.widget.SwitchCompat
import com.example.calmcode.utils.toast
import java.text.SimpleDateFormat
import java.util.Locale

class SettingsFragment : Fragment() {

    private lateinit var switchReminder: Switch
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvReminderTime: TextView

    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }


    private val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchReminder = view.findViewById(R.id.switchDailyReminder)
        tvUsername = view.findViewById(R.id.tvUsername)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvReminderTime = view.findViewById(R.id.tvReminderTime)

        val redditSwitch = view.findViewById<SwitchCompat>(R.id.switch_use_reddit)
        val prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        redditSwitch.isChecked = prefs.getBoolean("useReddit", false)

        redditSwitch.setOnCheckedChangeListener { _, isChecked ->
            toggleArticleSource(isChecked)
            requireContext().toast("Source: ${if (isChecked) "Reddit" else "News"}")
        }


        loadAccountInfo()
        setupReminderSwitch()
        setupTimePicker()

        updateDisplayedReminderTime()
    }
    private fun setupTimePicker() {
        tvReminderTime.setOnClickListener {
            val hour = sharedPrefs.getInt("reminder_hour", 8)
            val minute = sharedPrefs.getInt("reminder_minute", 0)

            val timePicker = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                sharedPrefs.edit()
                    .putInt("reminder_hour", selectedHour)
                    .putInt("reminder_minute", selectedMinute)
                    .apply()

                updateDisplayedReminderTime()

                if (switchReminder.isChecked) {
                    scheduleDailyReminder()
                }

            }, hour, minute, false)

            timePicker.show()
        }
    }
    private fun updateDisplayedReminderTime() {
        val hour = sharedPrefs.getInt("reminder_hour", 8)
        val minute = sharedPrefs.getInt("reminder_minute", 0)

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val formattedTime = timeFormat.format(calendar.time)

        tvReminderTime.text = "Reminder Time: $formattedTime"
    }
    private fun loadAccountInfo() {
        val username = sharedPrefs.getString("username", "Not set")
        val email = sharedPrefs.getString("email", "Not set")

        tvUsername.text = "Username: $username"
        tvEmail.text = "Email: $email"
    }

    private fun setupReminderSwitch() {
        val isReminderOn = sharedPrefs.getBoolean("daily_reminder", false)
        switchReminder.isChecked = isReminderOn

        switchReminder.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("daily_reminder", isChecked).apply()

            if (isChecked) {
                scheduleDailyReminder()
            } else {
                cancelDailyReminder()
            }
        }
    }

    private fun scheduleDailyReminder() {
        val intent = Intent(requireContext(), ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val hour = sharedPrefs.getInt("reminder_hour", 8)
        val minute = sharedPrefs.getInt("reminder_minute", 0)

        calendar.apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun cancelDailyReminder() {
        val intent = Intent(requireContext(), ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun toggleArticleSource(useReddit: Boolean) {
        val prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("useReddit", useReddit).apply()
    }

}