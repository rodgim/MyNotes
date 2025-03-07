package com.rodgim.mynotes.feature_note.data.repositories

import com.rodgim.mynotes.feature_note.domain.models.Note
import com.rodgim.mynotes.feature_note.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository : NoteRepository {

    private val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>> {
        return flow { emit(notes) }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    fun getNoteWithCorrectData() = Note(
        id = 0,
        title = "Test",
        content = "Description",
        timestamp = 1741352474,
        color = 1
    )

    fun getNoteWithEmptyTitle() = Note(
        id = 0,
        title = "",
        content = "Description",
        timestamp = 1741352474,
        color = 1
    )

    fun getNoteWithEmptyContent() = Note(
        id = 0,
        title = "Test",
        content = "",
        timestamp = 1741352474,
        color = 1
    )
}