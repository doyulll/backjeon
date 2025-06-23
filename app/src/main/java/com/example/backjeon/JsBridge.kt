package com.example.backjeon

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class JsBridge(private val context: Context) {

    @JavascriptInterface
    fun onGameResult(result: String) {
        // 결과에 따라 SharedPreferences에 저장
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        if (result == "승리") {
            val currentWins = prefs.getInt("wins", 0)
            editor.putInt("wins", currentWins + 1)
        } else if (result == "패배") {
            val currentLosses = prefs.getInt("losses", 0)
            editor.putInt("losses", currentLosses + 1)
        }

        editor.apply()

        // 토스트로 알림 표시
        Toast.makeText(context, "게임 결과: $result", Toast.LENGTH_SHORT).show()
    }
}
