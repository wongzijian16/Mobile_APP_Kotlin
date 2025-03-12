package com.example.naturemagnet

import android.app.*
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.naturemagnet.dao.ActivityDao
import com.example.naturemagnet.dao.ActivityJoinedDao
import com.example.naturemagnet.dao.CategoryDao
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentEventEditBinding
import com.example.naturemagnet.databinding.FragmentEventManageBinding
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.picker.DatePicker
import com.example.naturemagnet.picker.TimePicker
import com.example.naturemagnet.repository.EventRepository
import com.example.naturemagnet.viewModel.EventDetailsViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Year
import java.util.*
import java.util.Calendar.*


/**
 * A simple [Fragment] subclass.
 * Use the [event_edit.newInstance] factory method to
 * create an instance of this fragment.
 */
class event_edit : Fragment() {
    lateinit var binding: FragmentEventEditBinding
    private lateinit var db: NatureMagnetDB
    private lateinit var prefManager: PrefManager
    private val sharedViewModel: EventDetailsViewModel by activityViewModels()
    private lateinit var bitmap: Bitmap

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** local variables */
        binding = FragmentEventEditBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        db = NatureMagnetDB.getInstance(application)!!
        prefManager = PrefManager(application)
        val currentActivity = sharedViewModel.activity.value

        /** preparing repository instances for accessing database */
        val activityDao: ActivityDao = db.activityDao()
        val categoryDao: CategoryDao = db.categoryDao()
        val activityJoinedDao: ActivityJoinedDao = db.activityJoinedDao()
        val eventRepository = EventRepository(activityDao, categoryDao, activityJoinedDao)

        /** binding the activity object to livedata */
        binding.apply {
            eventName.text = currentActivity?.name
            descriptionInputTextField.setText(currentActivity?.descriptions.toString())
            activitySneakPeek.setImageBitmap(currentActivity?.sneakPeek)
            deadline.setText(currentActivity?.registrationDeadline.toString())

            /** get current activity's datetime and format it to be display */
            if (sharedViewModel.parent?.value.toString() != "ManageActivities")
                setupCreateAction(binding)
            else
                setupEditAction(this, currentActivity?.dateTime.toString())
            imageUploader.setOnClickListener {
                pickImageGallery()
            }
            cancelButton.setOnClickListener{
                it.findNavController().popBackStack()
            }
            saveButton.setOnClickListener {
                var updatedActivity = currentActivity

                if (::bitmap.isInitialized) {
                    Log.e("event_edit", "imange changed")
                    updatedActivity?.sneakPeek = bitmap
                }

                updatedActivity?.dateTime = date.text.toString() + " " + time.text.toString() +":00"
                updatedActivity?.descriptions = descriptionInputTextField.text.toString()
                eventRepository.updateActivity(updatedActivity!!)
                Toast.makeText(application,"Activity Details Has been succesfully updated !", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.eventManageFragment)
            }

            return binding.root
        }
    }

    fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, create_post.IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == create_post.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Glide.with(this)
                .asBitmap()
                .load(data?.data)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        @Nullable transition: Transition<in Bitmap?>?
                    ) {
                        binding.activitySneakPeek.setImageBitmap(resource)
                        bitmap = resource
                    }

                    override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                })
        }
    }
    fun setupEditAction(binding: FragmentEventEditBinding, dateTime: String) {
        binding.date.setText(dateTime.subSequence(0, 10))
        binding.time.setText(dateTime.subSequence(11, 16))

        val yearTemp = dateTime.subSequence(0, 4)
        val monthTemp = dateTime.subSequence(5, 7)
        val dayTemp = dateTime.subSequence(8, 10)
        val hourTemp = dateTime.subSequence(11, 13)
        val minuteTemp = dateTime.subSequence(14, 16)

        val  c = Calendar.getInstance();
        val year = yearTemp.toString().toInt()
        val month = monthTemp.toString().toInt()
        val day = dayTemp.toString().toInt()
        val hour = hourTemp.toString().toInt()
        val minute = minuteTemp.toString().toInt()
        val dateSetListener = OnDateSetListener { _, year, month, day ->
            val myFormat = "yyyy/MM/dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val tempMonth = month + 1
            var monthFormated:String = tempMonth.toString()
            var dayFormated:String = day.toString()
            if (tempMonth < 10)
                monthFormated = "0$monthFormated"
            if (day < 10)
                dayFormated = "0$dayFormated"
            binding.date.setText("$year/$monthFormated/$dayFormated")
        }
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            var hourFormated: String = hour.toString()
            var minuteFormated: String = minute.toString()
            if (hour < 10){
                hourFormated = "0$hour"
            }
            if (minute < 10)
                minuteFormated = "0$minute"
            binding.time.setText("$hourFormated:$minuteFormated")
        }

        var datePicker = DatePickerDialog(requireContext(), dateSetListener, year, month-1, day)
        var timePicker = TimePickerDialog(requireContext(), timeSetListener, hour, minute, false)

        binding.btnDate.setOnClickListener { datePicker.show() }
        binding.btnTime.setOnClickListener { timePicker.show() }
    }
    fun setupCreateAction(binding: FragmentEventEditBinding) {
        val  c = Calendar.getInstance();
        val sdf = SimpleDateFormat("yyyy/MM/dd/ hh:mm:ss")
        val todayDate = sdf.format(Date())
        //c.set(Calendar.DATE, todayDate.toLong().toInt())

        var year = c.get(YEAR)
        val month = c.get(MONTH)
        val day = c.get(DAY_OF_MONTH)
        val hour = 0
        val minute = 0

        binding.activitySneakPeek.setImageBitmap(null)
        binding.descriptionInputTextField.text = null
        val dateSetListener = OnDateSetListener { _, year, month, day ->
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            var month = month + 2
            binding.date.setText("$year/$month/$day")
            binding.deadline.setText("$year/$month/$day")
        }
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            var hourFormated: String = hour.toString()
            var minuteFormated: String = minute.toString()
            if (hour < 10){
                hourFormated = "0$hour"
            }
            if (minute < 10)
                minuteFormated = "0$minute"
            binding.time.setText("$hourFormated:$minuteFormated")
        }

        var datePicker = DatePickerDialog(requireContext(), dateSetListener, year, month, day)
        var timePicker = TimePickerDialog(requireContext(), timeSetListener, hour, minute, false)

        binding.btnDate.setOnClickListener { datePicker.show() }
        binding.btnTime.setOnClickListener { timePicker.show() }
    }
}