package com.rodgim.mynotes.feature_note.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.rodgim.mynotes.feature_note.data.repositories.FakeNoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetNoteUseCaseTest {

    private lateinit var getNote: GetNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setup() {
        fakeNoteRepository = FakeNoteRepository()
        getNote = GetNoteUseCase(fakeNoteRepository)
    }

    @Test
    fun `Get note, returns the correct note`() = runTest {
        val fakeNote = fakeNoteRepository.getNoteWithCorrectData()

        fakeNoteRepository.insertNote(fakeNote)
        val note = getNote(fakeNote.id)

        assertThat(fakeNote).isEqualTo(note)
    }
}