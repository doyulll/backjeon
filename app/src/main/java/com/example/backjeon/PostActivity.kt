package com.example.backjeon

import Comment
import Post
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class PostActivity : AppCompatActivity() {

    private val PREFS_NAME = "post_prefs"
    private val POSTS_KEY = "posts"

    private var postList = mutableListOf<Post>()
    private lateinit var adapter: PostAdapter
    private lateinit var prefs: android.content.SharedPreferences
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        // 다크모드 설정 적용
        val settingsPrefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedDarkMode = settingsPrefs.getBoolean("isDarkMode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (savedDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        postList = loadPostsFromPrefs().toMutableList()

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
                putParcelableArrayListExtra("comments", ArrayList(post.comments))
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
            val nickname = "사용자1"
            val timestamp = getTodayDateString()
            val newPost = Post(title, content, nickname, timestamp)
            postList.add(0, newPost)
            savePostsToPrefs()
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

    // SharedPreferences에 게시글 전체 저장
    private fun savePostsToPrefs() {
        val arr = JSONArray()
        postList.forEach { arr.put(it.toJson()) }
        prefs.edit().putString(POSTS_KEY, arr.toString()).apply()
    }

    // SharedPreferences에서 게시글 전체 불러오기
    private fun loadPostsFromPrefs(): List<Post> {
        val jsonStr = prefs.getString(POSTS_KEY, null) ?: return getDefaultPosts()
        val arr = JSONArray(jsonStr)
        val list = mutableListOf<Post>()
        for (i in 0 until arr.length()) {
            list.add(Post.fromJson(arr.getJSONObject(i)))
        }
        return list
    }

    // 앱 최초 실행 시 기본 게시글 3개
    private fun getDefaultPosts(): List<Post> {
        return listOf(
            Post(
                "초반 포석 어떻게 하시나요?",
                "요즘 한수돌 게임에서 좋은 수 찾는게 어렵네요.",
                "포석러",
                "2025-06-22",
                mutableListOf(
                    Comment("기마수", "기마부터 중앙에 두세요", "2025-06-22 14:22:11"),
                    Comment("장기러버", "저도 고민이에요", "2025-06-22 14:23:45")
                )
            ),
            Post(
                "AI 상대법 공유해요",
                "AI 패턴 파악해서 이렇게 하면 되더라구요~",
                "AI분석가",
                "2025-06-22",
                mutableListOf(
                    Comment("초반강한AI", "공감해요", "2025-06-22 14:24:33"),
                    Comment("분석중", "AI 초반이 세더라고요", "2025-06-22 14:25:00")
                )
            ),
            Post(
                "최고 승률 장기 전략",
                "현재 기준으로 제가 쓰는 전략을 공유해봅니다.",
                "전략가",
                "2025-06-22",
                mutableListOf(
                    Comment("감탄러", "좋은 전략이네요!", "2025-06-22 14:26:14"),
                    Comment("감사러", "감사합니다.", "2025-06-22 14:26:55")
                )
            )
        )
    }
}
