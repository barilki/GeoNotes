package com.example.geonotes.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geonotes.domain.model.Note
import com.example.geonotes.domain.repository.AuthRepository
import com.example.geonotes.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> = _uiState.asStateFlow()

    init {
        handleIntent(MainIntent.LoadNotes)
        loadUserName()
    }

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.ToggleDisplayMode -> toggleDisplayMode()
            is MainIntent.SignOut -> signOut()
            is MainIntent.LoadNotes -> loadNotes()
            is MainIntent.DeleteNote -> deleteNote(intent.note)
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                notesRepository.getAllNotes().collect { notes ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        notes = notes.sortedByDescending { it.createdAt },
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load notes"
                )
            }
        }
    }

    private fun toggleDisplayMode() {
        val currentMode = _uiState.value.displayMode
        val newMode = if (currentMode == DisplayMode.LIST) DisplayMode.MAP else DisplayMode.LIST
        _uiState.value = _uiState.value.copy(displayMode = newMode)
    }


    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                notesRepository.deleteNote(note)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete note"
                )
            }
        }
    }

    private fun loadUserName() {
        viewModelScope.launch {
            try {
                val userName = authRepository.getCurrentUserName() ?: "User"
                _uiState.value = _uiState.value.copy(userName = userName)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(userName = "User")
            }
        }
    }

    private fun signOut() {
//        viewModelScope.launch {
//            authRepository.signOut()
//            navigationManager.navigate(NavigationEvent.NavigateToLogin)
//        }
    }
}