package com.example.kmp_test.features.youtube.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun YoutubeBrowser(modifier: Modifier) {
    val browser = remember { WKWebView() }
    Column(modifier) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = { browser.goBack() }) { Text("웹 뒤로") }
            TextButton(onClick = { browser.reload() }) { Text("새로고침") }
        }
        UIKitView(
            factory = { browser.apply { loadRequest(NSURLRequest.requestWithURL(NSURL.URLWithString(YoutubeUrl)!!)) } },
            modifier = Modifier.weight(1f).fillMaxWidth(),
        )
    }
    DisposableEffect(browser) { onDispose { browser.stopLoading() } }
}
