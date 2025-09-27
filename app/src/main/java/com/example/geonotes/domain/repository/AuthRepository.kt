package com.example.geonotes.domain.repository

import com.example.geonotes.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun signUp(name: String, email: String, password: String): Flow<Resource<Boolean>>
    suspend fun signOut()
    fun getCurrentUser(): Flow<Boolean>
}