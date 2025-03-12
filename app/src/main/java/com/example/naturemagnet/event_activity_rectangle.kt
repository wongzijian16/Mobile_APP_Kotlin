package com.example.naturemagnet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class event_activity_rectangle : Fragment() {

    companion object {
        fun newInstance() = event_activity_rectangle()
    }

//    private lateinit var viewModel: EventRectangleActivityRowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_activity_rectangle, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(EventRectangleActivityRowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}