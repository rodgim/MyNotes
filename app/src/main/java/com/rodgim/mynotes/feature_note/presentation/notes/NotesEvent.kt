package com.rodgim.mynotes.feature_note.presentation.notes

import com.rodgim.mynotes.feature_note.domain.models.Note
import com.rodgim.mynotes.feature_note.domain.utils.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
