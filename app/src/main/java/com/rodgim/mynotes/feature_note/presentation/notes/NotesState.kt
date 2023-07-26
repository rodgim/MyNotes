package com.rodgim.mynotes.feature_note.presentation.notes

import com.rodgim.mynotes.feature_note.domain.models.Note
import com.rodgim.mynotes.feature_note.domain.utils.NoteOrder
import com.rodgim.mynotes.feature_note.domain.utils.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
