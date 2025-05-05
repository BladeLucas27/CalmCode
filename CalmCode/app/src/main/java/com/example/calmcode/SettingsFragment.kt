package com.example.calmcode

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.calmcode.utils.ReminderReceiver
import com.example.calmcode.utils.toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class SettingsFragment : Fragment() {

    private lateinit var switchReminder: SwitchCompat
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Daily Reminder"
            val descriptionText = "Reminds you to meditate daily"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("daily_reminder_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context?.getSystemService(NotificationManager::class.java)
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel)
            }
        }

        switchReminder = view.findViewById(R.id.switchDailyReminder)
        tvUsername = view.findViewById(R.id.tvUsername)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvReminderTime = view.findViewById(R.id.tvReminderTime)


        val about = view.findViewById<TextView>(R.id.about)
        val btnLogout = view.findViewById<Button>(R.id.btn_logout)
        val redditSwitch = view.findViewById<SwitchCompat>(R.id.switch_use_reddit)
        val articleSwitch = view.findViewById<SwitchCompat>(R.id.switch_use_reddit_for_articles)
        val prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        redditSwitch.isChecked = prefs.getBoolean("useReddit", false)

        redditSwitch.setOnCheckedChangeListener { _, isChecked ->
            toggleArticleSource(isChecked)
            requireContext().toast("Source: ${if (isChecked) "Reddit" else "News"}")
        }

        articleSwitch.isChecked = prefs.getBoolean("useRedditForArticles", false)
        articleSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("useRedditForArticles", isChecked).apply()
            requireContext().toast("Article: ${if (isChecked) "Reddit" else "News"}")
        }

        about.setOnClickListener {
            Toast.makeText(requireContext(), "Opening About Us Screen", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), DevelopersActivity::class.java))
        }
        btnLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Logging Out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            getActivity()?.finish()
            activity?.finish()
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
                }

                Log.d("SET REMINDER", "Reminder set!")
                scheduleDailyReminder()
            } else {
                cancelDailyReminder()
            }

        }
    }
    @SuppressLint("ScheduleExactAlarm")
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
//            timeInMillis = System.currentTimeMillis() + 30_000 // 30 seconds later

            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            // After setting hour, minute, second
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,
//            pendingIntent
//        )
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
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