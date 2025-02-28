package com.rodgim.mynotes.feature_note.data.repositories

import com.rodgim.mynotes.feature_note.data.source.NoteLocalDataSource
import com.rodgim.mynotes.feature_note.domain.models.Note
import com.rodgim.mynotes.feature_note.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalNoteRepository @Inject constructor(
    private val localDataSource: NoteLocalDataSource
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> = localDataSource.getNotes()

    override suspend fun getNoteById(id: Int): Note? = localDataSource.getNoteById(id)

    override suspend fun insertNote(note: Note) = localDataSource.insertNote(note)

    override suspend fun deleteNote(note: Note) = localDataSource.deleteNote(note)
}