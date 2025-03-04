package com.rodgim.mynotes.feature_note.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rodgim.mynotes.core.utils.TestTags
import com.rodgim.mynotes.feature_note.domain.models.Note
import com.rodgim.mynotes.feature_note.domain.utils.NoteOrder
import com.rodgim.mynotes.feature_note.presentation.notes.components.NoteItem
import com.rodgim.mynotes.feature_note.presentation.notes.components.OrderSection
import com.rodgim.mynotes.ui.NotesTopAppBar
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    onAddNote: () -> Unit,
    onClickNote: (Note) -> Unit,
    viewModel: NotesViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            NotesTopAppBar(
                onSortAllNotes = {
                    viewModel.onEvent(NotesEvent.ToggleOrderSection)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddNote()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note"
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        NotesContent(
            notes = state.notes,
            isOrderSectionVisible = state.isOrderSectionVisible,
            noteOrder = state.noteOrder,
            onClickNote = { note ->
                onClickNote(note)
            },
            onDeleteNote = { note ->
                viewModel.onEvent(NotesEvent.DeleteNote(note))
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Note deleted",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Long
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NotesEvent.RestoreNote)
                    }
                }
            },
            onOrderChange = { order ->
                viewModel.onEvent(NotesEvent.Order(order))
            },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun NotesContent(
    notes: List<Note>,
    isOrderSectionVisible: Boolean,
    noteOrder: NoteOrder,
    onClickNote: (Note) -> Unit,
    onDeleteNote: (Note) -> Unit,
    onOrderChange: (NoteOrder) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        /*Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your note",
                style = MaterialTheme.typography.headlineMedium
            )
            IconButton(
                onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }
            ) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort"
                )
            }
        }*/
        AnimatedVisibility(
            visible = isOrderSectionVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            OrderSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .testTag(TestTags.ORDER_SECTION),
                noteOrder = noteOrder,
                onOrderChange = {
                    onOrderChange(it)
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notes) { note ->
                NoteItem(
                    note = note,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClickNote(note)
                        },
                    onDeleteClick = {
                        onDeleteNote(note)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}