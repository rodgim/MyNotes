package com.rodgim.mynotes.di

import android.content.Context
import androidx.room.Room
import com.rodgim.mynotes.feature_note.data.repositories.LocalNoteRepository
import com.rodgim.mynotes.feature_note.data.room.NoteDatabase
import com.rodgim.mynotes.feature_note.data.room.datasources.RoomNoteLocalDataSource
import com.rodgim.mynotes.feature_note.data.source.NoteLocalDataSource
import com.rodgim.mynotes.feature_note.domain.repositories.NoteRepository
import com.rodgim.mynotes.feature_note.domain.usecases.AddNoteUseCase
import com.rodgim.mynotes.feature_note.domain.usecases.DeleteNoteUseCase
import com.rodgim.mynotes.feature_note.domain.usecases.GetNoteUseCase
import com.rodgim.mynotes.feature_note.domain.usecases.GetNotesUseCase
import com.rodgim.mynotes.feature_note.domain.usecases.NoteUseCases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context,
        NoteDatabase::class.java
    ).allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideNoteDao(database: NoteDatabase) = database.getNoteDao()

    @Singleton
    @Provides
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository)
        )
    }

    @Singleton
    @Provides
    fun provideNoteRepository(localDataSource: NoteLocalDataSource): NoteRepository {
        return LocalNoteRepository(localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindLocalDataSource(roomNoteLocalDataSource: RoomNoteLocalDataSource): NoteLocalDataSource

}
