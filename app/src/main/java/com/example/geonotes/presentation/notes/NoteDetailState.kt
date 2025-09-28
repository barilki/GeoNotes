package com.example.geonotes.presentation.notes

import com.example.geonotes.domain.model.Note

data class NoteDetailState(
    val note: Note? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)