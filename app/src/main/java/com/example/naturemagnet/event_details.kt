package com.example.naturemagnet

import android.database.sqlite.SQLiteConstraintException
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.naturemagnet.dao.ActivityDao
import com.example.naturemagnet.dao.ActivityJoinedDao
import com.example.naturemagnet.dao.CategoryDao
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentEventDetailsBinding
import com.example.naturemagnet.entity.ActivityJoined
import com.example.naturemagnet.repository.EventRepository
import com.example.naturemagnet.viewModel.EventDetailsViewModel
import java.util.*

class event_details : Fragment() {
    private var binding: FragmentEventDetailsBinding? = null
    private lateinit var prefManager: PrefManager

    /** viewModel important */
    private val sharedViewModel: EventDetailsViewModel by activityViewModels()
    private lateinit var db: NatureMagnetDB

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        val application = requireNotNull(this.activity).application
        db = NatureMagnetDB.getInstance(application)!!
        prefManager = PrefManager(application)
        val activityDao: ActivityDao = db.activityDao()
        val categoryDao: CategoryDao = db.categoryDao()
        val activityJoinedDao: ActivityJoinedDao = db.activityJoinedDao()
        val eventRepository = EventRepository(activityDao, categoryDao, activityJoinedDao)

        binding?.apply {
            /** using viewModel set on another fragment */
            val currentActivity = sharedViewModel.activity.value

            /** formate the date */
            val dateTime: String = currentActivity?.dateTime.toString()
            var date: String = dateTime.subSequence(0, 10) as String
            date = date.replace('-', '/')

            /** formate time of the activity */
            var time: String = dateTime.subSequence(11, 16) as String
            var hr = time.split(':')[0]
            val min = time.split(':')[1]
            if (hr.toInt() - 12 >= 0) {
                if (hr.toInt() - 12 > 0) {
                    hr = (hr.toInt() - 12).toString()
                }
                time = hr + " : " + min + " P.M."
            } else {
                time = hr + " : " + min + " A.M."
            }
            val formatedDateTime: String = date + "\nEvent Start @ " + time

            /** binding the data to the layout */
            binding!!.currentActivity = currentActivity
            detailsCardSneakpeek.setImageBitmap(currentActivity?.sneakPeek)
            detailsCardIcon.setImageBitmap(currentActivity?.sneakPeek)
            detailsCardDatetime.text = formatedDateTime

//            Log.e("event_details", sharedViewModel.parent?.value.toString())
            if (sharedViewModel.parent?.value.toString() == "ActivitiesJoined") {
                detailsCardJoinBtn.visibility = GONE
                detailsCardQuitBtn.visibility = VISIBLE
                detailsCardEditBtn.visibility = GONE
            }
            if (sharedViewModel.parent?.value.toString() == "ActivitiesToday") {
                detailsCardJoinBtn.visibility = VISIBLE
                detailsCardQuitBtn.visibility = GONE
                detailsCardEditBtn.visibility = GONE
            }
            if (sharedViewModel.parent?.value.toString() == "PopEvent") {
                detailsCardJoinBtn.visibility = VISIBLE
                detailsCardQuitBtn.visibility = GONE
                detailsCardEditBtn.visibility = GONE
            }
            if (sharedViewModel.parent?.value.toString() == "ManageActivities") {
                detailsCardJoinBtn.visibility = GONE
                detailsCardQuitBtn.visibility = GONE
                detailsCardEditBtn.visibility = VISIBLE
            }

            detailsCardJoinBtn.setOnClickListener {
                //TODO: insert an activityJoined entries using current custId & activityId
                if (currentActivity != null) {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH) + 1
                    val day = c.get(Calendar.DAY_OF_MONTH)

                    val sdf = SimpleDateFormat("yyyy/M/dd hh:mm:ss")
                    val currentDate = sdf.format(Date())

                    try {
                        var tempActivityJoined = ActivityJoined(
                            prefManager.getId()!!,
                            currentActivity.activityID,
                            currentDate
                        )
                        eventRepository.insertActivityJoined(tempActivityJoined)
                        it.findNavController().navigate(R.id.eventMainFragment)
                    } catch (exception: SQLiteConstraintException) {
                        Log.e("event_details", exception.toString())
                        Toast.makeText(
                            application,
                            "You Have Joined the Activity",
                            Toast.LENGTH_LONG
                        ).show()
                    }
//                    Log.e("Event_ Details", tempActivityJoined.toString())
                }
            }

            detailsCardQuitBtn.setOnClickListener {
                //TODO: delete specific activityJoined entries using with custId & activityId
                if (currentActivity != null) {
                    eventRepository.deleteActivityJoined(
                        currentActivity.activityID,
                        prefManager.getId()!!
                    )
                    it.findNavController().navigate(R.id.eventMainFragment)
                }
//                Log.e("Event_ Details", "join btn clicked")
            }

            detailsCardCancelBtn.setOnClickListener {
                //TODO: bring user back to previous fragment
                it.findNavController().popBackStack()
//                Log.e("Event_ Details", "join btn clicked")
            }

            detailsCardEditBtn.setOnClickListener{
                it.findNavController().navigate(R.id.eventEdit)
            }
        }
//        Log.e("event_details", sharedViewModel.activity.value.toString())
//        Log.e("event_details", sharedViewModel.num.value.toString())

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}