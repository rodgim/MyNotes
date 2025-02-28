package com.rodgim.mynotes.feature_note.data.mappers

import com.rodgim.mynotes.feature_note.domain.models.Note
import com.rodgim.mynotes.feature_note.data.room.entities.Note as NoteEntity

fun NoteEntity.toDomain() = Note(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    color = color
)

fun Note.toEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    color = color
)
