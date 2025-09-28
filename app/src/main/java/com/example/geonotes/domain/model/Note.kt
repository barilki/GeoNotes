package com.example.geonotes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Note (
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Parcelable