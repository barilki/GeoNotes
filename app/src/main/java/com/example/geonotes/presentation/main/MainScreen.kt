package com.example.geonotes.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.geonotes.domain.model.Note
import com.example.geonotes.presentation.main.components.EmptyNotes
import com.example.geonotes.presentation.main.components.NoteCard
import com.example.geonotes.presentation.main.components.NotesMap


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToCreateNote: () -> Unit = {},
    onNavigateToEditNote: (Note) -> Unit = {},
    onNavigateToNoteDetail: (Note) -> Unit = {},
    onSignOut: () -> Unit = {},
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Welcome, ${uiState.userName}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${uiState.notes.size} notes",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.handleIntent(MainIntent.ToggleDisplayMode) }
                    ) {
                        Icon(
                            imageVector = if (uiState.displayMode == DisplayMode.LIST) {
                                Icons.Default.Map
                            } else {
                                Icons.Default.List
                            },
                            contentDescription = "Toggle view mode"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToCreateNote() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.error ?: "Unknown error",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                uiState.notes.isEmpty() -> {
                    EmptyNotes(
                        onCreateFirstNote = {
                            onNavigateToCreateNote()
                        },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    when (uiState.displayMode) {
                        DisplayMode.LIST -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(uiState.notes) { note ->
                                    NoteCard(
                                        note = note,
                                        onClick = {
                                            onNavigateToNoteDetail(note)
                                        },
                                        onDelete = {
                                            viewModel.handleIntent(MainIntent.DeleteNote(note))
                                        },
                                        onUpdate = {
                                            onNavigateToEditNote(note)
                                        }
                                    )
                                }
                            }
                        }

                        DisplayMode.MAP -> {
                            NotesMap(
                                notes = uiState.notes,
                                onNoteClick = { note ->
                                    onNavigateToNoteDetail(note)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}