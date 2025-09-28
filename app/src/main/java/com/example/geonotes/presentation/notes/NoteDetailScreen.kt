package com.example.geonotes.presentation.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.geonotes.domain.model.Note
import com.example.geonotes.utils.DateUtils.formatDate
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Note) -> Unit,
    note: Note? = null,
    viewModel: NoteDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(note) {
        note?.let {
            viewModel.setNote(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkAndUpdateNote()
    }

    val uiState by viewModel.uiState.collectAsState()
    val currentNote = uiState.note ?: note

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    currentNote?.let { note ->
                        IconButton(
                            onClick = { onNavigateToEdit(note) }
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        currentNote?.let { note ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Note ID: ${note.id}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Created: ${formatDate(note.createdAt)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (note.createdAt != note.updatedAt) {
                            Text(
                                text = "Updated: ${formatDate(note.updatedAt)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Content",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = note.content,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                if (note.latitude != 0.0 && note.longitude != 0.0) {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Location",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = "Latitude: ${String.format("%.6f", note.latitude)}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "Longitude: ${String.format("%.6f", note.longitude)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("Note not found")
            }
        }
    }
}