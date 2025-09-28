package com.example.geonotes.presentation.main

sealed class MainIntent {
    object LoadNotes : MainIntent()
    object RefreshNotes : MainIntent()
    object ToggleDisplayMode : MainIntent()
    data class NavigateToNoteDetail(val noteId: String) : MainIntent()
    object NavigateToCreateNote : MainIntent()
    object SignOut : MainIntent()
}