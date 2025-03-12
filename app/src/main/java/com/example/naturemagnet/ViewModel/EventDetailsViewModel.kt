package com.example.naturemagnet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naturemagnet.entity.Activity

class EventDetailsViewModel : ViewModel() {
    private var _activity = MutableLiveData<Activity>()
    private var _parent: LiveData<String>? = MutableLiveData<String>()

    /** for others to access to the current activity */
    val activity: LiveData<Activity>
        get() = _activity
    val parent: LiveData<String>?
        get() = _parent

    init {
        _activity = MutableLiveData<Activity>()
        _parent = null
    }

    /** to allow the data to be updated by fragments/users */
    fun setActivity(activity: MutableLiveData<Activity>) {
        _activity = activity
    }
    fun setParent(parent: MutableLiveData<String>){
        _parent = parent
    }
}
