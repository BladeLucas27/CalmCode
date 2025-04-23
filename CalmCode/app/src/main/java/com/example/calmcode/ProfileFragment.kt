package com.example.calmcode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.graphics.Paint
import android.graphics.Shader
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.calmcode.app.myApplication
import com.example.calmcode.utils.toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.yalantis.ucrop.UCrop
import java.io.File
import kotlin.math.min
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class ProfileFragment : Fragment() {

    private val UCROP_REQUEST_CODE = 69
    private val PICK_IMAGE_REQUEST = 1001
    private lateinit var ivProfilePic: ImageView

    private lateinit var tvBirthday: TextView
    private lateinit var btnEditBirthday: ImageButton

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.fragment_profile, container, false)
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        ivProfilePic = view.findViewById(R.id.ivProfilePic)

        ivProfilePic.setOnClickListener {
            pickImageFromGallery()
        }

        loadSavedProfileImage()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        val tvEmail = view.findViewById<TextView>(R.id.tv_email)

        tvBirthday = view.findViewById(R.id.tv_birthday)
        val btnEditBirthday = view.findViewById<ImageButton>(R.id.btn_edit_birthday)
        btnEditBirthday.setOnClickListener {
            Log.d("BirthdayPicker", "Edit birthday clicked!")
            showBirthdayPicker()
        }


//        btnEditBirthday.setOnClickListener {
//            showBirthdayPicker()
//        }

        // Set user data
        tvUsername.text = (requireActivity().application as myApplication).getUsername()
        tvEmail.text = (requireActivity().application as myApplication).getEmail()
        loadBirthday()?.let {
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            tvBirthday.text = "Birthday: ${sdf.format(it)}"
        } ?: run {
            tvBirthday.text = "Birthday: Not set"
        }

        // Click listeners for edit icons
        view.findViewById<ImageButton>(R.id.btn_edit_username).setOnClickListener {
            requireContext().toast("Edit Username")
        }


        // Set stat values — later you can load these dynamically from SharedPreferences or DB
        view.findViewById<TextView>(R.id.tv_streak).text = "🔥 0 days\nCurrent Streak"
        view.findViewById<TextView>(R.id.tv_longest).text = "📅 2 days\nLongest Streak"
        view.findViewById<TextView>(R.id.tv_tracks).text = "▶️ 9\nTotal Tracks Completed"
        view.findViewById<TextView>(R.id.tv_minutes).text = "⏳ 33 minutes\nTotal Time Listened"
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val sourceUri = data?.data ?: return
            val destUri = Uri.fromFile(File(requireContext().cacheDir, "cropped.jpg"))

            val intent = UCrop.of(sourceUri, destUri)
                .withAspectRatio(1f, 1f)
                .withMaxResultSize(512, 512)
                .getIntent(requireContext())

            startActivityForResult(intent, UCROP_REQUEST_CODE)
        } else if (requestCode == UCROP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                saveImageUri(it)
                setImage(it)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun setImage(uri: Uri) {
        try {
            val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
            val bitmap = ImageDecoder.decodeBitmap(source)
            ivProfilePic.setImageBitmap(getCircularBitmap(bitmap))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCircularBitmap(srcBitmap: Bitmap): Bitmap {
        val size = min(srcBitmap.width, srcBitmap.height)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

        // Create a software canvas
        val canvas = Canvas(output)

        val paint = Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(
                Bitmap.createBitmap(srcBitmap).copy(Bitmap.Config.ARGB_8888, false),
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
            )
        }

        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        return output
    }
    private fun saveImageUri(uri: Uri) {
        val prefs = requireContext().getSharedPreferences("profile", Context.MODE_PRIVATE)
        prefs.edit().putString("profileImageUri", uri.toString()).apply()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun loadSavedProfileImage() {
        val prefs = requireContext().getSharedPreferences("profile", Context.MODE_PRIVATE)
        val uriString = prefs.getString("profileImageUri", null)
        uriString?.let {
            setImage(Uri.parse(it))
        }
    }


    private fun showBirthdayPicker() {
        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select your birthday")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        datePicker.show(parentFragmentManager, "BIRTHDAY_PICKER")

        datePicker.addOnPositiveButtonClickListener { selection ->
            val date = Date(selection)
            val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val formattedDate = sdf.format(date)

            tvBirthday.text = "Birthday: $formattedDate"
            saveBirthday(date)
        }

    }


    private fun saveBirthday(date: Date) {
        val prefs = requireContext().getSharedPreferences("profile", Context.MODE_PRIVATE)
        prefs.edit().putLong("birthday", date.time).apply()
    }

    private fun loadBirthday(): Date? {
        val prefs = requireContext().getSharedPreferences("profile", Context.MODE_PRIVATE)
        val time = prefs.getLong("birthday", -1)
        return if (time != -1L) Date(time) else null
    }
}
