package com.example.geonotes.presentation.notes

data class AddNoteState(
    val isLoading: Boolean = false,
    val isNoteSaved: Boolean = false,
    val error: String? = null
)