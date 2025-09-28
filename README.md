# GeoNotes Android App

A location-aware note-taking application built with modern Android development practices using Jetpack Compose and Clean Architecture.

## Architecture Overview

**Clean Architecture** with clear separation across three layers:
- **Domain Layer**: Business logic, entities (`Note`), repository interfaces
- **Data Layer**: Repository implementations and data sources
- **Presentation Layer**: Jetpack Compose UI with ViewModels

**MVI Pattern** for predictable state management:
- **Model**: UI state (`MainState`, `NoteDetailState`)
- **View**: Composable functions rendering based on state
- **Intent**: User actions (`MainIntent`) triggering state changes

## Key Features

- **Dual Display**: List view and interactive map view with location markers
- **Note Management**: Create, read, update, delete 
- **Location Integration**: Geographic visualization of notes

## Tech Stack

- **Jetpack Compose** - Modern declarative UI
- **Hilt** - Dependency injection
- **StateFlow** - Reactive state management
- **Navigation Compose** - Type-safe navigation
- **Material 3** - Design system

## Project Structure

```
app/src/main/java/com/example/geonotes/
├── domain/
│   ├── model/
│   │   ├── Note.kt              # Note entity with location data
│   │   └── Resource.kt          # Wrapper for API responses
│   └── repository/
│       └── NotesRepository.kt   # Repository interface
├── data/
│   └── repository/
│       └── NotesRepositoryImpl.kt # Repository implementation
├── presentation/
│   ├── main/
│   │   ├── components/
│   │   │   ├── EmptyNotes.kt    # Empty state component
│   │   │   ├── NoteCard.kt      # Note list item
│   │   │   └── NotesMap.kt      # Map view component
│   │   ├── MainScreen.kt        # Main screen with dual view modes
│   │   ├── MainViewModel.kt     # Main screen business logic
│   │   ├── MainState.kt         # UI state definition
│   │   └── MainIntent.kt        # User action definitions
│   ├── notes/
│   │   ├── NoteDetailScreen.kt  # Note detail/edit screen
│   │   ├── NoteDetailViewModel.kt # Note detail business logic
│   │   └── NoteDetailState.kt   # Note detail UI state
│   ├── auth/
│   │   ├── LoginScreen.kt       # Authentication screen
│   │   └── AuthViewModel.kt     # Authentication logic
│   ├── navigation/
│   │   └── Navigation.kt        # App navigation setup
│   └── MainActivity.kt          # Main activity entry point
└── di/
    └── AppModule.kt             # Dependency injection configuration    
   
```

## Current Limitations


- **Authentication**: No persistent login sessions or logout functionality
- **UI Actions**: Missing edit/delete buttons in note detail screen
- No search, filtering, or note organization
- Basic error handling
- **No unit tests**: ViewModels and business logic lack test coverage
- **No UI tests**: Compose screens and user interactions untested
- **No integration tests**: Repository and data layer functionality not validated

## Getting Started

1. Clone repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run application

