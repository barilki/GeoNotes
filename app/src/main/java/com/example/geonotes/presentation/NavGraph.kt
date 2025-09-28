package com.example.geonotes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.geonotes.domain.model.Note
import com.example.geonotes.presentation.auth.LoginScreen
import com.example.geonotes.presentation.auth.SignUpScreen
import com.example.geonotes.presentation.main.MainScreen
import com.example.geonotes.presentation.notes.AddNoteScreen
import com.example.geonotes.presentation.notes.NoteDetailScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Route.LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.LOGIN) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Route.SIGN_UP)
                },
                onNavigateToMain = {
                    navController.navigate(Route.MAIN) {
                        popUpTo(Route.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Route.SIGN_UP) {
            SignUpScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToMain = {
                    navController.navigate(Route.MAIN) {
                        popUpTo(Route.SIGN_UP) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Route.ADD_NOTE) {
            val note = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Note>("note")

            AddNoteScreen(
                onNavigateBack = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.remove<Note>("note")
                    navController.popBackStack()
                },
                note = note
            )
        }

        composable(Route.NOTE_DETAIL) {
            val note = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Note>("note_detail")

            NoteDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEdit = { noteToEdit ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("note", noteToEdit)
                    navController.navigate(Route.ADD_NOTE)
                },
                note = note
            )
        }

        composable(Route.MAIN) {
            MainScreen(
                onNavigateToCreateNote = {
                    navController.currentBackStackEntry?.savedStateHandle?.remove<Note>("note")
                    navController.navigate(Route.ADD_NOTE)
                },
                onNavigateToEditNote = { note ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("note", note)
                    navController.navigate(Route.ADD_NOTE)
                },
                onNavigateToNoteDetail = { note ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("note_detail", note)
                    navController.navigate(Route.NOTE_DETAIL)
                },
                onSignOut = {
                    navController.navigate(Route.LOGIN) {
                        popUpTo(Route.MAIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }


    }
}

object Route {
    const val LOGIN = "login"
    const val SIGN_UP = "sign_up"
    const val MAIN = "main"
    const val ADD_NOTE = "add_note"
    const val NOTE_DETAIL = "note_detail"
}