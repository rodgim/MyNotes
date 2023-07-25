package com.rodgim.mynotes.feature_note.domain.repositories

import com.rodgim.mynotes.feature_note.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}