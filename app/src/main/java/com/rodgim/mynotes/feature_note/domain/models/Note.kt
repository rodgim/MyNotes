package com.rodgim.mynotes.feature_note.domain.models

data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
)
