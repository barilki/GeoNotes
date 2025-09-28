package com.example.geonotes.data.repository

import com.example.geonotes.data.local.NotesDatabase
import com.example.geonotes.data.mapper.toNote
import com.example.geonotes.data.mapper.toNoteEntity
import com.example.geonotes.domain.model.Note
import com.example.geonotes.domain.model.Resource
import com.example.geonotes.domain.repository.AuthRepository
import com.example.geonotes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepositoryImpl @Inject constructor(
    noteDb: NotesDatabase,
) : NotesRepository {

    private val noteDao = noteDb.noteDao()

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map { it.toNote() }
        }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return try {
            return noteDao.getNoteById(id)?.toNote()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun createNote(note: Note): Resource<Unit> {
        return try {
            val entity = note.toNoteEntity()
            noteDao.upsertNoteEntity(entity)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to create note")
        }
    }

    override suspend fun updateNote(note: Note): Resource<Unit> {
        return try {
            val entity = note.toNoteEntity()
            noteDao.upsertNoteEntity(entity)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update note")
        }
    }

    override suspend fun deleteNote(note: Note): Resource<Unit> {
        return try {
            val entity = note.toNoteEntity()
            noteDao.deleteNoteEntity(entity)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to delete note")
        }
    }
}