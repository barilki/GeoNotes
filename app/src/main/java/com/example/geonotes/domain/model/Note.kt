package com.example.geonotes.domain.model

import java.time.LocalDateTime


data class Note (
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)