package com.example.kmp_test.features.youtube.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

const val YoutubeUrl = "https://m.youtube.com"

// Platform implementations keep native view APIs outside the shared screen.
@Composable
expect fun YoutubeBrowser(modifier: Modifier = Modifier)
