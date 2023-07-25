package com.rodgim.mynotes.feature_note.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodgim.mynotes.feature_note.data.room.entities.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}