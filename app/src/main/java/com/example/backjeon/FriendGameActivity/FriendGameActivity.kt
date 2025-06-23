package com.example.backjeon

import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FriendGameActivity : AppCompatActivity() {

    // JavaScript에서 호출할 수 있는 브릿지 클래스 정의
    @JavascriptInterface
    fun onWinDetected(result: String) {
        runOnUiThread {
            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
            val wins = prefs.getInt("wins", 0)
            val losses = prefs.getInt("losses", 0)

            // 값 갱신
            if (result == "승리") {
                prefs.edit().putInt("wins", wins + 1).apply()
            } else {
                prefs.edit().putInt("losses", losses + 1).apply()
            }

            // 결과화면 이동 생략 가능 (직접 메인으로 돌아가도 됨)
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true

        webView.webViewClient = WebViewClient()

        // JS와 통신 가능한 브릿지 등록
        webView.addJavascriptInterface(JsBridge(this), "AndroidBridge")


        webView.loadUrl("file:///android_asset/friends_game.html")
    }
}
