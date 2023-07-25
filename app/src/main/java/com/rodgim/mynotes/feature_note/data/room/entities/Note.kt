package com.rodgim.mynotes.feature_note.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
)
