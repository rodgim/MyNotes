package com.rodgim.mynotes.feature_note.presentation.add_edit_note

import androidx.annotation.StringRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rodgim.mynotes.core.utils.TestTags
import com.rodgim.mynotes.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import com.rodgim.mynotes.feature_note.presentation.utils.noteColors
import com.rodgim.mynotes.ui.AddEditNoteTopAppBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    @StringRes topBarTitle: Int,
    onBack: () -> Unit,
    onSaveNote: () -> Unit,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val snackbarHostState = remember { SnackbarHostState() }

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                AddEditNoteViewModel.UiEvent.SaveNote -> {
                    onSaveNote()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AddEditNoteTopAppBar(topBarTitle) {
                onBack()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save note"
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        AddEditNoteContent(
            title = titleState,
            content = contentState,
            noteColor = viewModel.noteColor.value,
            backgroundColor = noteBackgroundAnimatable.value,
            onEnteredTitle = { title ->
                viewModel.onEvent(AddEditNoteEvent.EnteredTitle(title))
            },
            onChangeTitleFocus = { titleFocus ->
                viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(titleFocus))
            },
            onEnteredContent = { content ->
                viewModel.onEvent(AddEditNoteEvent.EnteredContent(content))
            },
            onChangeContentFocus = { contentFocus ->
                viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(contentFocus))
            },
            onChangeNoteColor = { newNoteColor ->
                scope.launch {
                    noteBackgroundAnimatable.animateTo(
                        targetValue = Color(newNoteColor),
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                }
                viewModel.onEvent(AddEditNoteEvent.ChangeColor(newNoteColor))
            },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun AddEditNoteContent(
    title: NoteTextFieldState,
    content: NoteTextFieldState,
    noteColor: Int,
    backgroundColor: Color,
    onEnteredTitle: (String) -> Unit,
    onChangeTitleFocus: (FocusState) -> Unit,
    onEnteredContent: (String) -> Unit,
    onChangeContentFocus: (FocusState) -> Unit,
    onChangeNoteColor: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            noteColors.forEach { color ->
                val colorInt = color.toArgb()
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = 3.dp,
                            color = if (noteColor == colorInt) {
                                Color.Black
                            } else {
                                Color.Transparent
                            },
                            shape = CircleShape
                        )
                        .clickable {
                            onChangeNoteColor(colorInt)
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = title.text,
            hint = title.hint,
            onValueChange = {
                onEnteredTitle(it)
            },
            onFocusChange = {
                onChangeTitleFocus(it)
            },
            isHintVisible = title.isHintVisible,
            singleLine = true,
            textStyle = MaterialTheme.typography.headlineSmall,
            testTag = TestTags.TITLE_TEXT_FIELD
        )
        Spacer(modifier = Modifier.height(16.dp))
        TransparentHintTextField(
            text = content.text,
            hint = content.hint,
            onValueChange = {
                onEnteredContent(it)
            },
            onFocusChange ={
                onChangeContentFocus(it)
            },
            isHintVisible = content.isHintVisible,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxHeight(),
            testTag = TestTags.CONTENT_TEXT_FIELD
        )
    }
}