package com.example.kmp_test.features.youtube.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmp_test.features.youtube.components.YoutubeBrowser

@Composable
fun YoutubeScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            TextButton(onClick = onBack) { Text("‹ 목록으로") }
            Text("유튜브", fontSize = 24.sp)
        }
        YoutubeBrowser(Modifier.weight(1f).fillMaxWidth())
    }
}
