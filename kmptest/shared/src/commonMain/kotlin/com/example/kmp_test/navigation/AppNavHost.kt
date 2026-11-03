package com.example.kmp_test.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.read
import com.example.kmp_test.features.tasks.screens.TaskListScreen
import com.example.kmp_test.features.tasks.screens.TaskDetailScreen
import com.example.kmp_test.features.tasks.state.TasksState

import com.example.kmp_test.features.youtube.screens.YoutubeScreen

private const val LIST = "tasks"
private const val DETAIL = "tasks/{id}"

@Composable
fun AppNavHost(state: TasksState, onNavHostReady: suspend (NavController) -> Unit) {
    val navController = rememberNavController()
    fun backToList() {
        if (!navController.popBackStack()) navController.navigate(LIST) { launchSingleTop = true }
    }
    NavHost(navController = navController, startDestination = LIST,
        modifier = Modifier.widthIn(max = 600.dp).fillMaxSize()) {
        composable(LIST) {
            TaskListScreen(state = state, onOpen = { navController.navigate("tasks/$it") },
                onOpenYoutube = { navController.navigate("youtube") })
        }
        composable("youtube") { YoutubeScreen(onBack = ::backToList) }
        composable(DETAIL) { entry ->
            val id = entry.arguments?.read { getString("id") }?.toIntOrNull()
            TaskDetailScreen(
                task = state.tasks.find { it.id == id },
                onBack = ::backToList,
                onToggle = { id?.let(state::toggle) },
                onDelete = { id?.let(state::remove); backToList() },
            )
        }
    }
    LaunchedEffect(navController) { onNavHostReady(navController) }
}
