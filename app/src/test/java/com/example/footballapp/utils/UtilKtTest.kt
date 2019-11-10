package com.example.footballapp.utils

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class UtilKtTest {

    @Test
    fun dateFormat() {
        val formatDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        formatDate.timeZone = TimeZone.getTimeZone("GMT")
        val date = formatDate.parse("2019-07-02 08:00:00")
        val dateText = SimpleDateFormat("EEEE, dd MMM yyyy\nHH:mm", Locale.getDefault())
            .format(date).toString()
        assertEquals("Tuesday, 02 Jul 2019\n15:00", dateText)

    }
}