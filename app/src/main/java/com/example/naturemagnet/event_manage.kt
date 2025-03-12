package com.example.naturemagnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naturemagnet.adapter.EventActivityClickkListener
import com.example.naturemagnet.adapter.EventRectangleAdapter
import com.example.naturemagnet.dao.ActivityDao
import com.example.naturemagnet.dao.ActivityJoinedDao
import com.example.naturemagnet.dao.CategoryDao
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.FragmentEventManageBinding
import com.example.naturemagnet.entity.Activity
import com.example.naturemagnet.repository.EventRepository
import com.example.naturemagnet.viewModel.EventDetailsViewModel


class event_manage : Fragment(), EventActivityClickkListener {
    lateinit var binding: FragmentEventManageBinding
    private lateinit var db: NatureMagnetDB
    private lateinit var prefManager: PrefManager
    private lateinit var listener: EventActivityClickkListener
    private val sharedViewModel: EventDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** local variables */
        binding = FragmentEventManageBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        db = NatureMagnetDB.getInstance(application)!!
        prefManager = PrefManager(application)
        listener = this

        /** preparing repository instances for accessing database */
        val activityDao: ActivityDao = db.activityDao()
        val categoryDao: CategoryDao = db.categoryDao()
        val activityJoinedDao: ActivityJoinedDao = db.activityJoinedDao()
        val eventRepository = EventRepository(activityDao, categoryDao, activityJoinedDao)

        /** get all activities the specific user posted and display in a RecyclerView */
        val layoutManager = LinearLayoutManager(application)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val eventRectangleAdapter = EventRectangleAdapter(eventRepository.getAllActivities(), this, "ManageActivities")
        binding.yourActivityCardRecyclerView.layoutManager = layoutManager
        binding.yourActivityCardRecyclerView.adapter = eventRectangleAdapter

        binding.createActivityBtn.setOnClickListener{
            it.findNavController().navigate(R.id.eventEdit)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityClick(view: View, activity: Activity, layout: String) {
        val tempActivity = MutableLiveData<Activity>(activity)
        val tempParent = MutableLiveData<String>(layout)

        /** updating data in viewModel */
        sharedViewModel.setActivity(tempActivity)
        sharedViewModel.setParent(tempParent)

        view.findNavController().navigate(R.id.event_details)
//        Log.e("Event_Main_Fragment", sharedViewModel.num.value.toString())
//        Log.e("Event_Main_Fragment", sharedViewModel.activity.value.toString())
    }
}