package com.example.backjeon

import android.content.Intent
import android.content.SharedPreferences
import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    private lateinit var messageTextView: TextView
    private var matchingGame = ""
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // SharedPreferences 초기화
        prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedDarkMode = prefs.getBoolean("isDarkMode", false)

        // 다크모드 적용
        AppCompatDelegate.setDefaultNightMode(
            if (savedDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageTextView = findViewById(R.id.matching_message)

        val toggleButton = findViewById<Button>(R.id.themeToggleButton)
        toggleButton.setOnClickListener {
            val newMode = !prefs.getBoolean("isDarkMode", false)
            prefs.edit().putBoolean("isDarkMode", newMode).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (newMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            recreate()
        }

        val cardAI = findViewById<CardView>(R.id.card_ai)
        val cardFriends = findViewById<CardView>(R.id.card_friends)
        val cardCommunity = findViewById<CardView>(R.id.card_community)

        cardAI.setOnClickListener {
            if (matchingGame.isEmpty()) handleAIMatch()
        }

        cardFriends.setOnClickListener {
            if (matchingGame.isEmpty()) {
                Toast.makeText(this, "친구와 플레이를 시작합니다.", Toast.LENGTH_SHORT).show()
                // TODO: 친구 초대 페이지 연결 예정
            }
        }

        cardCommunity.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleAIMatch() {
        matchingGame = "Play with AI"
        messageTextView.text = "AI 상대를 준비 중입니다..."

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AIGameActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}
