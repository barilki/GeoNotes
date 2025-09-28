package com.example.geonotes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.geonotes.presentation.auth.LoginScreen
import com.example.geonotes.presentation.auth.SignUpScreen
import com.example.geonotes.presentation.main.MainScreen
import com.example.geonotes.presentation.notes.AddNoteScreen

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
             AddNoteScreen(
                 onNavigateBack = {
                        navController.popBackStack()
                 }
             )
        }

        composable(Route.NOTE_DETAIL) {

        }

        composable(Route.MAIN) {
            MainScreen(
                onNavigateToCreateNote = { navController.navigate(Route.ADD_NOTE) },
                onNavigateToNoteDetail = { /* TODO */ },
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
    const val EDIT_NOTE = "edit_note"
    const val NOTE_DETAIL = "note_detail/{noteId}"
}