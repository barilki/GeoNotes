package com.example.geonotes.domain.repository

import com.example.geonotes.domain.model.Note
import com.example.geonotes.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: String): Note?
    suspend fun createNote(note: Note): Resource<Unit>
    suspend fun updateNote(note: Note): Resource<Unit>
    suspend fun deleteNote(note: Note): Resource<Unit>
}