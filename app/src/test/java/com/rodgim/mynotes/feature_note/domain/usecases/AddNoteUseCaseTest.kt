package com.rodgim.mynotes.feature_note.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.rodgim.mynotes.feature_note.data.repositories.FakeNoteRepository
import com.rodgim.mynotes.feature_note.domain.exceptions.InvalidNoteException
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class AddNoteUseCaseTest {
    private lateinit var addNote: AddNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        addNote = AddNoteUseCase(fakeNoteRepository)
    }

    @Test
    fun `Insert note, correct insertion`() = runTest {
        val note = fakeNoteRepository.getNoteWithCorrectData()

        addNote(note)

        assertThat(fakeNoteRepository.getNoteById(note.id)).isEqualTo(note)
    }

    @Test
    fun `Insert note with empty title, get InvalidNoteException`() = runTest {
        val note = fakeNoteRepository.getNoteWithEmptyTitle()

        assertFailsWith<InvalidNoteException>{ addNote(note) }
    }

    @Test
    fun `Insert note with empty title, get exception with correct message`() = runTest {
        val note = fakeNoteRepository.getNoteWithEmptyTitle()

        val exception = assertFailsWith<InvalidNoteException>{ addNote(note) }

        assertThat("The title of the note can't be empty.").isEqualTo(exception.message)
    }

    @Test
    fun `Insert note with empty content, get InvalidNoteException`() = runTest {
        val note = fakeNoteRepository.getNoteWithEmptyContent()

        assertFailsWith<InvalidNoteException>{ addNote(note) }
    }

    @Test
    fun `Insert note with empty content, get exception with correct message`() = runTest {
        val note = fakeNoteRepository.getNoteWithEmptyContent()

        val exception = assertFailsWith<InvalidNoteException>{ addNote(note) }

        assertThat("The content of the note can't be empty.").isEqualTo(exception.message)
    }
}