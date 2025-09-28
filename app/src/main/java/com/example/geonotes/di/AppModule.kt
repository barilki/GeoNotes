package com.example.geonotes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.geonotes.data.local.NotesDatabase
import com.example.geonotes.data.location.LocationManager
import com.example.geonotes.data.repository.AuthRepositoryImpl
import com.example.geonotes.data.repository.NotesRepositoryImpl
import com.example.geonotes.domain.repository.AuthRepository
import com.example.geonotes.domain.repository.NotesRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository{
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideNoteDb(application: Application): NotesDatabase {
        return Room.databaseBuilder(
            application,
            NotesDatabase::class.java,
            "note_db.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(
        noteDb: NotesDatabase,
        authRepository: AuthRepository,
    ): NotesRepository {
        return NotesRepositoryImpl(noteDb, authRepository)
    }

    @Provides
    @Singleton
    fun provideLocationManager(
        @ApplicationContext context: Context
    ): LocationManager {
        return LocationManager(context)
    }
}