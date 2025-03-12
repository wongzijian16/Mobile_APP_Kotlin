package com.example.naturemagnet.picker

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePicker: DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, android.text.format.DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(p0: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        Log.e("TimePicker", hourOfDay.toString() + " : " + minute.toString())
    }
}