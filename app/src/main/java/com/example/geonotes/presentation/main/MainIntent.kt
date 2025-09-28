package com.example.geonotes.presentation.main

import com.example.geonotes.domain.model.Note

sealed class MainIntent {
    data object LoadNotes : MainIntent()
    data object ToggleDisplayMode : MainIntent()
    data object SignOut : MainIntent()
    data class DeleteNote(val note: Note) : MainIntent()
}