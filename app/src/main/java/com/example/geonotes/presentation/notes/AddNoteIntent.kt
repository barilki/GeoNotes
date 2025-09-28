package com.example.geonotes.presentation.notes

import com.example.geonotes.domain.model.Note

sealed class NoteIntent {
    data class CreateNote(val title: String, val content: String) : NoteIntent()
    data class UpdateNote(val note: Note, val title: String, val content: String) : NoteIntent()
    data class DeleteNote(val note: Note) : NoteIntent()
    object ClearError : NoteIntent()
    object ResetState : NoteIntent()
}