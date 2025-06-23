package com.example.backjeon

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class AIGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true

        webView.webViewClient = WebViewClient()

        // 브릿지 등록
        webView.addJavascriptInterface(JsBridge(this), "AndroidBridge")

        webView.loadUrl("file:///android_asset/ai_game.html")
    }
}
