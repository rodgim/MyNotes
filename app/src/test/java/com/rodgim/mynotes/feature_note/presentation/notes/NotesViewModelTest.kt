package com.rodgim.mynotes.feature_note.presentation.notes

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.rodgim.mynotes.MainCoroutineRule
import com.rodgim.mynotes.feature_note.data.repositories.FakeNoteRepository
import com.rodgim.mynotes.feature_note.domain.models.Note
import com.rodgim.mynotes.feature_note.domain.usecases.AddNoteUseCase
import com.rodgim.mynotes.feature_note.domain.usecases.DeleteNoteUseCase
import com.rodgim.mynotes.feature_note.domain.usecases.GetNoteUseCase
import com.rodgim.mynotes.feature_note.domain.usecases.GetNotesUseCase
import com.rodgim.mynotes.feature_note.domain.usecases.NoteUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {

    private lateinit var notesViewModel: NotesViewModel

    private lateinit var noteRepository: FakeNoteRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        noteRepository = FakeNoteRepository()
        val note1 = Note(id = 0, title = "Title1", content = "Content1", timestamp = 1741352474, color = -21615)
        val note2 = Note(id = 1, title = "Title", content = "Content2", timestamp = 1741352574, color = -1577573)
        val note3 = Note(id = 2, title = "Title", content = "Content3", timestamp = 1741352874, color = -8266006)
        noteRepository.addNotes(note1, note2, note3)

        notesViewModel = NotesViewModel(
            NoteUseCases(
                GetNotesUseCase(noteRepository),
                DeleteNoteUseCase(noteRepository),
                AddNoteUseCase(noteRepository),
                GetNoteUseCase(noteRepository)
            ),
            SavedStateHandle()
        )
    }

    @Test
    fun `load All Notes from repository`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        assertThat(notesViewModel.state.first().notes).hasSize(3)
    }
}