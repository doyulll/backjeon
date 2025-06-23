package com.example.backjeon

import android.content.Intent
import android.content.SharedPreferences
import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import android.view.animation.AnimationUtils
import android.view.MotionEvent

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

        //  승률 ProgressBar, TextView 설정
        val winRateBar = findViewById<ProgressBar>(R.id.winRateProgress)
        val winRateText = findViewById<TextView>(R.id.winRateText)

        val wins = prefs.getInt("wins", 0)
        val losses = prefs.getInt("losses", 0)
        val total = wins + losses
        val winRate = if (total > 0) (wins * 100 / total) else 0

        winRateBar.progress = winRate
        winRateText.text = "승률: $winRate%"

        //  테마 토글 버튼
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
            if (matchingGame.isEmpty()) handleFriendMatch()
        }

        cardCommunity.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        setCardHoverAnimation(cardAI)
        setCardHoverAnimation(cardFriends)
        setCardHoverAnimation(cardCommunity)

    }


    private fun handleAIMatch() {
        matchingGame = "Play with AI"
        messageTextView.text = "AI 상대를 준비 중입니다..."

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AIGameActivity::class.java)
            startActivity(intent)
        }, 2000)
    }

    private fun handleFriendMatch() {
        matchingGame = "Play with Friend"
        messageTextView.text = "친구와 대국을 준비 중입니다..."

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, FriendGameActivity::class.java)
            startActivity(intent)
        }, 2000)
    }

    private fun setCardHoverAnimation(card: CardView) {
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)

        card.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> v.startAnimation(scaleUp)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> v.startAnimation(scaleDown)
            }
            false
        }
    }

}
