package com.example.naturemagnet.entity

data class AQI(
    val aqiIndex : String,
    val cityName : String,
    /*
    * 0-5 Good (#009966) (Green)
    * 51-100 Moderate (#FFDE33) (yellow)
    * 101-300 Unhealthy (#CC0033) (red)
    * > 300 Hazardous (#7E0023) (dark red)
    * */
    val status : String,
    val color : String,
    val updateDateTime : String
)