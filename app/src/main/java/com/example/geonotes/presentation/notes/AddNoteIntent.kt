package com.example.geonotes.presentation.notes

sealed class NoteIntent {
    data class CreateNote(val title: String, val content: String) : NoteIntent()
    data class UpdateNote(val noteId: String, val title: String, val content: String) : NoteIntent()
    data class DeleteNote(val noteId: String) : NoteIntent()
    object ClearError : NoteIntent()
    object ResetState : NoteIntent()
}