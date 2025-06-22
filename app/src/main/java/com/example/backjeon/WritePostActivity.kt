package com.example.backjeon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WritePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        val titleInput = findViewById<EditText>(R.id.input_post_title)
        val contentInput = findViewById<EditText>(R.id.input_post_content)
        val submitButton = findViewById<Button>(R.id.button_submit_post)

        submitButton.setOnClickListener {
            val title = titleInput.text.toString()
            val content = contentInput.text.toString()
            val nickname = "익명" // 또는 사용자 입력받기
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

            val resultIntent = Intent().apply {
                putExtra("title", title)
                putExtra("content", content)
                putExtra("nickname", nickname)
                putExtra("date", date)
            }

            if (title.isNotBlank() && content.isNotBlank()) {
                val resultIntent = Intent().apply {
                    putExtra("title", title)
                    putExtra("content", content)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
