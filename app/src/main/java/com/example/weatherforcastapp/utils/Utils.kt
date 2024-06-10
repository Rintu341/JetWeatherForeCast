package com.example.weatherforcastapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatDate(timestamp:Int):String{
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}
fun fahrenheitToCelsius(fahrenheit:Double):String{
    return ((fahrenheit - 32)/1.8f).toInt().toString()
}

fun formatJustDate(unixTime: Int): String {
    val date = Date(unixTime * 1000L)
    val sdf = SimpleDateFormat("EEE", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}


fun formatDateTime(unixTime: Int): String {
    //convert it into second to millisecond
    val date = Date(unixTime * 1000L)
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}
fun formatDateTimeInNumber(unixTime: Int): String {
    val date = Date(unixTime * 1000L)
    val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}