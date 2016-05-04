package com.andela.checksmarter.utilities

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by CodeKenn on 29/04/16.
 */
object DateFormatter {
    fun getReadableDate(milliSeconds: Long): String {
        val simpleDateFormat = SimpleDateFormat("MMM dd, yyyy - hh:mm a")
        val date = Date(milliSeconds)
        return simpleDateFormat.format(date)
    }

    fun getReadableTime(milliSeconds: Long): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm a")
        val date = Date(milliSeconds)
        return simpleDateFormat.format(date)
    }

    fun getReadableSeconds(milliSeconds: Long): String {
        val simpleDateFormat = SimpleDateFormat("ss")
        val date = Date(milliSeconds)
        return simpleDateFormat.format(date)
    }

    fun getCurrentTime(): Long {
        return Calendar.getInstance().getTimeInMillis()
    }
}