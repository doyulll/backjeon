// src/main/java/com/backjeon/MainActivity.kt
package com.example.backjeon

import android.content.Intent
import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.backjeon.R
import kotlin.jvm.java
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var messageTextView: TextView
    private var matchingGame = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageTextView = findViewById(R.id.matching_message)

        val cardQuickMatch = findViewById<CardView>(R.id.card_quick_match)
        val cardAI = findViewById<CardView>(R.id.card_ai)
        val cardFriends = findViewById<CardView>(R.id.card_friends)

        cardQuickMatch.setOnClickListener {
            if (matchingGame.isEmpty()) handleQuickMatch()
        }

        cardAI.setOnClickListener {
            if (matchingGame.isEmpty()) handleAIMatch()
        }

        cardFriends.setOnClickListener {
            if (matchingGame.isEmpty()) {
                Toast.makeText(this, "Starting Play with Friends", Toast.LENGTH_SHORT).show()
                // TODO: 친구 초대 페이지 연결 예정
            }
        }
    }

    private fun handleQuickMatch() {
        matchingGame = "Quick Match"
        messageTextView.text = "Matching... Please wait while we find an opponent!"

        Handler(Looper.getMainLooper()).postDelayed({
            val roomId = Random.nextInt(100000)
            val intent = Intent(this, AIGameActivity::class.java)
            intent.putExtra("roomId", roomId)
            startActivity(intent)
        }, 2000)
    }

    private fun handleAIMatch() {
        matchingGame = "Play with AI"
        messageTextView.text = "Setting up AI opponent..."

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AIGameActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}
