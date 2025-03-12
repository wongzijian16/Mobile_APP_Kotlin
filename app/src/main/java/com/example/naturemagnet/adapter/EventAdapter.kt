package com.example.naturemagnet.adapter

import android.R.attr.bitmap
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.naturemagnet.R
import com.example.naturemagnet.databinding.FragmentEventActivityBinding
import com.example.naturemagnet.entity.Activity
import java.text.DecimalFormat

class EventAdapter(private val activities: List<Activity>, private val activityClickListener: EventActivityClickkListener, private val layout: String) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    /** define the attribute according to the fragment to bind data on it later */
    class EventViewHolder(val binding: FragmentEventActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: Activity) {
            val decimalFormat = DecimalFormat("###,###")
            val participantNum = activity.participants
            var participants = decimalFormat.format(participantNum).toString() + "\nParticipants"

            binding.activity = activity
            (binding.cardBackgroundActivity as ImageView).setImageBitmap(activity.sneakPeek)
            binding.cardIconActivity.setImageBitmap(activity.sneakPeek)
            binding.participants = participants

//            binding.rectangleCardIconActivity?.setImageBitmap(activity.sneakPeek)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentEventActivityBinding.inflate(layoutInflater, parent, false)
        return EventViewHolder(binding)
    }

    /** bind the data from the current object to the fragment's attribute defined in EventViewHolder() */
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(activities[position])
        holder.binding.root.setOnClickListener{
            activityClickListener.onActivityClick(it, activities[position], layout)
        }
//        Log.e("Adapter", currentActivity.toString())
    }

    /** get numbers of activity to be load into the recycler view */
    override fun getItemCount() = activities.size
}