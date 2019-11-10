package com.example.footballapp.utils

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun dateFormat(date: String?): String {
    val formatDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    formatDate.timeZone = TimeZone.getTimeZone("GMT")
    val matchDate = formatDate.parse(date)
    val dateText = SimpleDateFormat("EEEE, dd MMM yyyy\nHH:mm", Locale.getDefault())
        .format(matchDate).toString()
    return dateText
}


