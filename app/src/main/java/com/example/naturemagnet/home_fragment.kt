package com.example.naturemagnet

import android.graphics.Color
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.naturemagnet.adapter.AqiAdapter
import com.example.naturemagnet.databinding.FragmentHomeBinding
import com.example.naturemagnet.datagenerator.SampleDataGenerator
import com.example.naturemagnet.entity.AQI
import com.example.naturemagnet.volleyAPi.VolleySingleton
import org.json.JSONException
import org.json.JSONObject

class home_fragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var manager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        val application = requireNotNull(this.activity).application

        val URL: String =
            "https://api.waqi.info/v2/map/bounds?latlng=3.157764,101.711861,6.443589,100.216599&networks=all&token=744061a3de0d632b6e2ddb6ed97e158a5dee41e4"
        val json = JsonObjectRequest(Request.Method.GET, URL, null,
            Response.Listener { response ->
                try {
                    val obj: JSONObject = response
                    val array = obj.getJSONArray("data")

                    val tempList = mutableListOf<AQI>()

                    for (i in 0 until array.length()) {
                        val aqiCity: JSONObject = array.getJSONObject(i)
                        val aqi = aqiCity.getString("aqi")
                        val station = aqiCity.getJSONObject("station")
                        val name = station.getString("name")
                        val updateDateTime = station.getString("time")
                        val nameList = name.split(',').map { it.trim() }
                        var status = ""
                        var color = ""

                        /*
                        * 0-50 Good (#009966) (Green)
                        * 51-100 Moderate (#FFDE33) (yellow)
                        * 101-300 Unhealthy (#CC0033) (red)
                        * > 300 Hazardous (#7E0023) (dark red)
                        * */
                        if (!aqi.equals("-")) {
                            when (aqi.toInt()) {
                                in 0..50 -> {
                                    status = "Good"
                                    color = "#009966"
                                }
                                in 51..100 -> {
                                    status = "Moderate"
                                    color = "#FFDE33"
                                }
                                in 101..300 -> {
                                    status = "Unhealthy"
                                    color = "#CC0033"
                                }
                                else -> {
                                    status = "Hazardous"
                                    color = "#7E0023"
                                }

                            }
                            tempList.add(AQI(aqi, nameList[0], status, color, updateDateTime))
                        }
                    }

                    //card1
                    binding.cityName1.text = tempList[0].cityName
                    binding.aqi1.text = tempList[0].aqiIndex.toString()
                    binding.aqiStatus1.text = tempList[0].status
                    binding.card1.setCardBackgroundColor(Color.parseColor(tempList[0].color))

                    //card2
                    binding.cityName2.text = tempList[1].cityName
                    binding.aqi2.text = tempList[1].aqiIndex.toString()
                    binding.aqiStatus2.text = tempList[1].status
                    binding.card2.setCardBackgroundColor(Color.parseColor(tempList[1].color))

                    //card3
                    binding.cityName3.text = tempList[2].cityName
                    binding.aqi3.text = tempList[2].aqiIndex.toString()
                    binding.aqiStatus3.text = tempList[2].status
                    binding.card3.setCardBackgroundColor(Color.parseColor(tempList[2].color))

                    manager = LinearLayoutManager(application)
                    binding.aqiRecyclerView.apply {
                        adapter = AqiAdapter(tempList)
                        layoutManager = manager
                    }


                } catch (e: JSONException) {
                    Toast.makeText(application, "Failed Fetch data from API", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("TAG", e.toString())
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(application, "Failed Fetch data from API", Toast.LENGTH_SHORT).show()
            }
        )

        VolleySingleton.getInstance(application).addToRequestQueue(json)

        return binding.root
    }


}