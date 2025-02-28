package com.rodgim.mynotes.feature_note.data.room.datasources

import com.rodgim.mynotes.feature_note.data.source.NoteLocalDataSource
import com.rodgim.mynotes.feature_note.data.room.NoteDao
import com.rodgim.mynotes.feature_note.data.mappers.toDomain
import com.rodgim.mynotes.feature_note.data.mappers.toEntity
import com.rodgim.mynotes.feature_note.domain.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomNoteLocalDataSource(
    private val noteDao: NoteDao
) : NoteLocalDataSource {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes().map { it.map { note -> note.toDomain() } }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)?.toDomain()
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }
}