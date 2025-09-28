package com.example.geonotes.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {

    fun formatDate(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy  HH:mm", Locale.getDefault())
        return dateTime.format(formatter)
    }

    fun formatTimestamp(timestamp: Long): String {
        val date = java.util.Date(timestamp)
        val formatter = java.text.SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.getDefault())
        return formatter.format(date)
    }
}