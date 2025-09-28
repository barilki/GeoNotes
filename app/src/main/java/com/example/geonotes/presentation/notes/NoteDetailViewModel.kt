package com.example.geonotes.presentation.notes

import androidx.lifecycle.ViewModel
import com.example.geonotes.domain.model.Note
import com.example.geonotes.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteDetailState())
    val uiState: StateFlow<NoteDetailState> = _uiState.asStateFlow()

    fun setNote(note: Note) {
        _uiState.value = _uiState.value.copy(note = note)
    }

    suspend fun checkAndUpdateNote() {
        val currentNote = _uiState.value.note ?: return

        try {
            val latestNote = notesRepository.getNoteById(currentNote.id)

            if (latestNote != null && latestNote != currentNote) {
                _uiState.value = _uiState.value.copy(note = latestNote)
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(error = e.message ?: "Failed to update")
        }
    }
}