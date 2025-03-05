package com.rodgim.mynotes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rodgim.mynotes.R
import com.rodgim.mynotes.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.rodgim.mynotes.feature_note.presentation.notes.NotesScreen
import com.rodgim.mynotes.ui.NoteDestinationsArgs.NOTE_COLOR_ARG
import com.rodgim.mynotes.ui.NoteDestinationsArgs.NOTE_ID_ARG
import com.rodgim.mynotes.ui.NoteDestinationsArgs.TITLE_ARG
import kotlinx.coroutines.CoroutineScope

@Composable
fun NoteNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = NoteDestinations.NOTES_ROUTE,
    navActions: NoteNavigationActions = remember(navController) {
        NoteNavigationActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            NoteDestinations.NOTES_ROUTE
        ) {
            NotesScreen(
                onAddNote = { navActions.navigateToAddEditNote(R.string.new_note, null, null) },
                onClickNote = { note ->
                    navActions.navigateToAddEditNote(R.string.edit_note, note.id, note.color)
                }
            )
        }

        composable(
            NoteDestinations.ADD_EDIT_NOTE_ROUTE,
            arguments = listOf(
                navArgument(TITLE_ARG) {
                    type = NavType.IntType
                },

                navArgument(NOTE_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(NOTE_COLOR_ARG) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { entry ->
            AddEditNoteScreen(
                topBarTitle = entry.arguments?.getInt(TITLE_ARG)!!,
                onBack = { navController.popBackStack() },
                onSaveNote = { navActions.navigateToNotes() },
                noteColor = entry.arguments?.getInt(NOTE_COLOR_ARG)!!
            )
        }
    }
}