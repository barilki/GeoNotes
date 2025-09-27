package com.example.geonotes.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geonotes.domain.model.Resource
import com.example.geonotes.domain.repository.AuthRepository
import com.example.geonotes.presentation.auth.intent.AuthIntent
import com.example.geonotes.presentation.auth.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.Login -> signIn()
            is AuthIntent.EmailChange -> onEmailChange(intent.email)
            is AuthIntent.PasswordChange -> onPasswordChange(intent.password)
            else -> {}
        }
    }

    private fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, error = null)
    }

    private fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, error = null)
    }


    private fun signIn() {
        val currentState = _uiState.value
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.value = currentState.copy(error = "Please fill in all fields")
            return
        }

        viewModelScope.launch {
            authRepository.signIn(currentState.email, currentState.password).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.value = currentState.copy(isLoading = true, error = null)
                    }
                    is Resource.Success -> {
                        _uiState.value = currentState.copy(
                            isLoading = false,
                            isSuccess = true,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = currentState.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
}