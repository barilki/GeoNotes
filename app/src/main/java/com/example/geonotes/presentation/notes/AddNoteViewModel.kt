package com.example.geonotes.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geonotes.data.location.LocationManager
import com.example.geonotes.domain.model.Note
import com.example.geonotes.domain.model.Resource
import com.example.geonotes.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val locationManager: LocationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddNoteState())
    val uiState: StateFlow<AddNoteState> = _uiState.asStateFlow()

    fun handleIntent(intent: NoteIntent) {
        when (intent) {
            is NoteIntent.CreateNote -> createNote(intent.title, intent.content)
            is NoteIntent.ClearError -> clearError()
            is NoteIntent.ResetState -> resetState()
            is NoteIntent.DeleteNote -> TODO()
            is NoteIntent.UpdateNote -> TODO()
        }
    }

    private fun createNote(title: String, content: String) {
        if (title.isBlank() || content.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {

                val location = locationManager.getCurrentLocation()

                val note = Note().copy(
                    title = title.trim(),
                    content = content.trim(),
                    longitude =  location?.longitude ?: 0.0,
                    latitude = location?.latitude ?: 0.0,
                )

                val res = notesRepository.createNote(note)
                if(res is Resource.Success) {
                    _uiState.value = _uiState.value.copy(isLoading = false, isNoteSaved = true, error = null)
                } else if(res is Resource.Error) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = res.message
                    )
                }
                _uiState.value = _uiState.value.copy(isLoading = false, error = null)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to create note"
                )
            }
        }
    }

    private fun updateNote(original: Note, title: String, content: String) {
        if (title.isBlank() || content.isBlank()) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val updated = original.copy(
                    title = title.trim(),
                    content = content.trim(),
                )
                when (val res = notesRepository.updateNote(updated)) {
                    is Resource.Success -> _uiState.value = _uiState.value.copy(isLoading = false, isNoteSaved = true)
                    is Resource.Error -> _uiState.value = _uiState.value.copy(isLoading = false, error = res.message)
                    Resource.Loading -> TODO()
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Failed to update note")
            }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun resetState() {
        _uiState.value = AddNoteState()
    }
}