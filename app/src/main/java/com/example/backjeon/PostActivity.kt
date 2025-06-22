package com.example.backjeon

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class PostActivity : AppCompatActivity() {

    private val postList = mutableListOf(
        Post(
            title = "초반 포석 어떻게 하시나요?",
            content = "요즘 한수돌 게임에서 좋은 수 찾는게 어렵네요.",
            nickname = "포석러",
            timestamp = "2025-06-22",
            comments = mutableListOf(
                Comment("기마수", "기마부터 중앙에 두세요", "2025-06-22 14:22:11"),
                Comment("장기러버", "저도 고민이에요", "2025-06-22 14:23:45")
            )
        ),
        Post(
            title = "AI 상대법 공유해요",
            content = "AI 패턴 파악해서 이렇게 하면 되더라구요~",
            nickname = "AI분석가",
            timestamp = "2025-06-22",
            comments = mutableListOf(
                Comment("초반강한AI", "공감해요", "2025-06-22 14:24:33"),
                Comment("분석중", "AI 초반이 세더라고요", "2025-06-22 14:25:00")
            )
        ),
        Post(
            title = "최고 승률 장기 전략",
            content = "현재 기준으로 제가 쓰는 전략을 공유해봅니다.",
            nickname = "전략가",
            timestamp = "2025-06-22",
            comments = mutableListOf(
                Comment("감탄러", "좋은 전략이네요!", "2025-06-22 14:26:14"),
                Comment("감사러", "감사합니다.", "2025-06-22 14:26:55")
            )
        )
    )


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "커뮤니티"
        }

        recyclerView = findViewById(R.id.recyclerViewPosts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PostAdapter(postList) { post ->
            val intent = Intent(this, PostDetailActivity::class.java).apply {
                putExtra("title", post.title)
                putExtra("content", post.content)
                putExtra("nickname", post.nickname)
                putExtra("timestamp", post.timestamp)
                putExtra("comments", ArrayList(post.comments)) // Parcelable Comment 필요
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fab_write).setOnClickListener {
            val intent = Intent(this, WritePostActivity::class.java)
            startActivityForResult(intent, 1001)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK && data != null) {
            val title = data.getStringExtra("title") ?: return
            val content = data.getStringExtra("content") ?: return
            val nickname = "익명 사용자"
            val timestamp = getTodayDateString()

            val newPost = Post(title, content, nickname, timestamp)
            postList.add(0, newPost)
            adapter.notifyItemInserted(0)
            recyclerView.scrollToPosition(0)
        }
    }

    private fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showPostDialog(post: Post) {
        val dialogView = LayoutInflater.from(this)
            .inflate(android.R.layout.simple_list_item_2, null)
        val titleView = dialogView.findViewById<TextView>(android.R.id.text1)
        val contentView = dialogView.findViewById<TextView>(android.R.id.text2)

        titleView.text = post.title
        contentView.text = post.content

        AlertDialog.Builder(this)
            .setTitle("게시글 상세")
            .setView(dialogView)
            .setPositiveButton("닫기", null)
            .show()
    }
}
