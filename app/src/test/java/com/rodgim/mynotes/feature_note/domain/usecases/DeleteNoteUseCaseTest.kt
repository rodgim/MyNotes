package com.rodgim.mynotes.feature_note.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.rodgim.mynotes.feature_note.data.repositories.FakeNoteRepository
import com.rodgim.mynotes.feature_note.domain.models.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {

    private lateinit var deleteNote: DeleteNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNoteUseCase(fakeNoteRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Delete note, correct deletion`() = runTest {
        val note = fakeNoteRepository.getNoteWithCorrectData()

        fakeNoteRepository.insertNote(note)
        fakeNoteRepository.deleteNote(note)
        val notes: List<Note> = fakeNoteRepository.getNotes().flatMapConcat { it.asFlow() }.toList()

        assertThat(notes).isEmpty()
    }
}