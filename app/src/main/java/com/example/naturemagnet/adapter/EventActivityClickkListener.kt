package com.example.naturemagnet.adapter

import android.view.View
import com.example.naturemagnet.entity.Activity

interface EventActivityClickkListener {
    fun onActivityClick(view: View, activity: Activity, layout: String)
}