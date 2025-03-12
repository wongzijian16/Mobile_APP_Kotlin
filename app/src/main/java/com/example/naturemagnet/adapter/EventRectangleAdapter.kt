package com.example.naturemagnet.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.naturemagnet.R
import com.example.naturemagnet.databinding.FragmentEventActivityBinding
import com.example.naturemagnet.databinding.FragmentEventActivityRectangleBinding
import com.example.naturemagnet.entity.Activity
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class EventRectangleAdapter(private val activities: List<Activity>, private val activityClickListener: EventActivityClickkListener, private val layout: String) :
    RecyclerView.Adapter<EventRectangleAdapter.EventRectangleViewHolder>() {

    /** define the attribute according to the fragment to bind data on it later */
    class EventRectangleViewHolder(val binding: FragmentEventActivityRectangleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: Activity) {
            val decimalFormat = DecimalFormat("###,###")
            val participantNum = activity.participants
            var participants = decimalFormat.format(participantNum).toString() + " Participants"

            binding.activity = activity
            binding.rectangleCardBackgroundActivity.setImageBitmap(activity.sneakPeek)
            binding.rectangleCardIconActivity.setImageBitmap(activity.sneakPeek)
            binding.participants = participants
            var date: String = activity.dateTime?.subSequence(5, 10).toString()
            binding.date = date.replace('-', '/')
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventRectangleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentEventActivityRectangleBinding.inflate(layoutInflater, parent, false)
////        val itemView = layoutInflater.inflate(
////            R.layout.fragment_event_activity_rectangle,
////            parent, false
////        )
        return EventRectangleViewHolder(binding)
    }
//
    /** bind the data from the current object to the fragment's attribute defined in EventViewHolder() */
    override fun onBindViewHolder(holder: EventRectangleViewHolder, position: Int) {
        holder.bind(activities[position])
        holder.binding.root.setOnClickListener{
            activityClickListener.onActivityClick(it, activities[position], layout)
        }
//        holder.itemView.setOnClickListener { view ->
//            view.findNavController().navigate(R.id.event_details)
////            Log.e("rectangle_event_adapter", "Clicked on item. $position")
//        }
////        Log.e("Adapter", currentActivity.toString())
    }

    /** get numbers of activity to be load into the recycler view */
    override fun getItemCount() = activities.size
}