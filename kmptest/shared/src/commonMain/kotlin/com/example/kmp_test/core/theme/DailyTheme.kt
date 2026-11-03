package com.example.kmp_test.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Accent = Color(0xFF5265D9)
val Background = Color(0xFFF5F7FB)
@Composable
fun DailyTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = lightColorScheme(primary = Accent, background = Background), content = content)
}
