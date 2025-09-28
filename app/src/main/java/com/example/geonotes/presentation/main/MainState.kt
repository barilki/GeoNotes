package com.example.geonotes.presentation.main

import com.example.geonotes.domain.model.Note

data class MainState(
    val userName: String = "",
    val notes: List<Note> = emptyList(),
    val displayMode: DisplayMode = DisplayMode.LIST,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class DisplayMode {
    LIST, MAP
}