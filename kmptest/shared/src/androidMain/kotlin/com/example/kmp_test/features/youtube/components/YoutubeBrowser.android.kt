package com.example.kmp_test.features.youtube.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun YoutubeBrowser(modifier: Modifier) {
    var webView by remember { mutableStateOf<WebView?>(null) }
    var loading by remember { mutableStateOf(true) }
    var canGoBack by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    Column(modifier) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = { webView?.goBack() }, enabled = canGoBack) { Text("웹 뒤로") }
            TextButton(onClick = { error = false; webView?.reload() }) { Text("새로고침") }
        }
        if (loading) LinearProgressIndicator(Modifier.fillMaxWidth())
        if (error) Text("유튜브를 불러오지 못했어요. 새로고침해 주세요.")
        AndroidView(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webChromeClient = WebChromeClient()
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean =
                            request.url.scheme != "https" && request.url.toString() != "about:blank"
                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) { loading = true; error = false }
                        override fun onPageFinished(view: WebView, url: String?) { loading = false; canGoBack = view.canGoBack() }
                        override fun onReceivedError(view: WebView, request: WebResourceRequest, failure: WebResourceError) {
                            if (request.isForMainFrame) { error = true; loading = false }
                        }
                    }
                    webView = this
                    loadUrl(YoutubeUrl)
                }
            },
            onReset = null,
            onRelease = { it.stopLoading(); it.destroy(); webView = null },
        )
    }
}
