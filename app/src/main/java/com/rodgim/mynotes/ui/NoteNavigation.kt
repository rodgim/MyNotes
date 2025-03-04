package com.rodgim.mynotes.ui

import androidx.navigation.NavHostController
import com.rodgim.mynotes.ui.NoteDestinationsArgs.NOTE_COLOR_ARG
import com.rodgim.mynotes.ui.NoteDestinationsArgs.NOTE_ID_ARG
import com.rodgim.mynotes.ui.NoteScreens.ADD_EDIT_NOTE_SCREEN
import com.rodgim.mynotes.ui.NoteScreens.NOTES_SCREEN

private object NoteScreens {
    const val NOTES_SCREEN = "notes"
    const val ADD_EDIT_NOTE_SCREEN = "addEditNote"
}

object NoteDestinationsArgs {
    const val NOTE_ID_ARG = "noteId"
    const val NOTE_COLOR_ARG = "noteColor"
}

object NoteDestinations {
    const val NOTES_ROUTE = NOTES_SCREEN
    const val ADD_EDIT_NOTE_ROUTE = "$ADD_EDIT_NOTE_SCREEN?$NOTE_ID_ARG={$NOTE_ID_ARG}&$NOTE_COLOR_ARG={$NOTE_COLOR_ARG}"
}

class NoteNavigationActions(
    private val navController: NavHostController
) {
    fun navigateToNotes() {
        navController.navigate(NoteDestinations.NOTES_ROUTE) {
            launchSingleTop = true
        }
    }

    fun navigateToAddEditNote(noteId: Int?, color: Int?) {
        navController.navigate(
            ADD_EDIT_NOTE_SCREEN.let {
                if (noteId != null && color != null) "$it?$NOTE_ID_ARG=$noteId&$NOTE_COLOR_ARG=$color" else it
            }
        )
    }
}
