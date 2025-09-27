package com.example.geonotes.presentation.auth.intent

sealed class AuthIntent {
    data class Login(val email: String, val password: String) : AuthIntent()
    data class SignUp(val name: String, val email: String, val password: String) : AuthIntent()
    data class NameChange(val name: String) : AuthIntent()
    data class EmailChange(val email: String) : AuthIntent()
    data class PasswordChange(val password: String) : AuthIntent()
}