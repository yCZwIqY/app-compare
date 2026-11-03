package com.example.kmp_test.features.youtube.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BrowserFallback(modifier: Modifier) {
    val uriHandler = LocalUriHandler.current
    var error by remember { mutableStateOf(false) }
    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("YouTube", fontSize = 36.sp, color = Color.Red)
        Text("브라우저에서 유튜브를 열어보세요.")
        Button(onClick = {
            try { uriHandler.openUri("https://www.youtube.com") } catch (_: Exception) { error = true }
        }) { Text("브라우저에서 열기 ↗") }
        if (error) Text("브라우저를 열지 못했어요.")
    }
}
