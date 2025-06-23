package com.example.backjeon

import android.os.Bundle
import android.util.TypedValue
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.HtmlCompat

class PostDetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var commentInput: EditText
    private lateinit var addCommentButton: Button
    private lateinit var commentTextView: TextView
    private lateinit var commentSection: LinearLayout

    private var comments: MutableList<Comment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. 테마 적용 (super.onCreate() 이전)
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedDarkMode = prefs.getBoolean("isDarkMode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (savedDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "게시글 상세"

        titleTextView = findViewById(R.id.detail_title)
        contentTextView = findViewById(R.id.detail_content)
        commentInput = findViewById(R.id.comment_input)
        addCommentButton = findViewById(R.id.comment_submit)
        commentTextView = findViewById(R.id.text_comments)
        commentSection = findViewById(R.id.comment_section)

        // 게시글 정보 받기
        val title = intent.getStringExtra("title") ?: ""
        val content = intent.getStringExtra("content") ?: ""
        comments = intent.getParcelableArrayListExtra("comments") ?: mutableListOf()

        titleTextView.text = title
        contentTextView.text = content

        updateComments()

        addCommentButton.setOnClickListener {
            val commentString = commentInput.text.toString().trim()
            if (commentString.isNotBlank()) {
                val newComment = Comment(
                    nickname = "사용자1",
                    content = commentString,
                    timestamp = getCurrentTime()
                )
                comments.add(newComment)
                commentInput.text.clear()
                updateComments()
            }
        }
    }


    private fun updateComments() {
        commentSection.removeAllViews()

        if (comments.isEmpty()) {
            commentTextView.text = "댓글이 없습니다"
        } else {
            commentTextView.text = ""
            for (comment in comments) {
                val commentView = TextView(this)
                val displayText = "<b>${comment.nickname}</b> (${comment.timestamp})<br>${comment.content}"
                commentView.text = HtmlCompat.fromHtml(displayText, HtmlCompat.FROM_HTML_MODE_LEGACY)
                // 여기서 색상 리소스 사용!
                commentView.setTextColor(getColor(R.color.text_primary))
                commentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                commentView.setPadding(0, 8, 0, 8)
                commentSection.addView(commentView)
            }
        }
    }



    private fun getCurrentTime(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(java.util.Date())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
