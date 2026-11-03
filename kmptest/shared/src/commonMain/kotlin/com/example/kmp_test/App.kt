package com.example.kmp_test

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.kmp_test.core.theme.DailyTheme
import com.example.kmp_test.features.tasks.state.TasksState
import com.example.kmp_test.navigation.AppNavHost

@Composable
fun App(onNavHostReady: suspend (NavController) -> Unit = {}) {
    val state = remember { TasksState() }
    DailyTheme {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Box(Modifier.fillMaxSize().safeDrawingPadding().imePadding(), contentAlignment = Alignment.TopCenter) {
                AppNavHost(state, onNavHostReady)
            }
        }
    }
}
