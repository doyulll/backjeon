import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject

@Parcelize
data class Comment(
    val nickname: String,
    val content: String,
    val timestamp: String
) : Parcelable {
    fun toJson(): JSONObject = JSONObject().apply {
        put("nickname", nickname)
        put("content", content)
        put("timestamp", timestamp)
    }
    companion object {
        fun fromJson(obj: JSONObject): Comment = Comment(
            nickname = obj.getString("nickname"),
            content = obj.getString("content"),
            timestamp = obj.getString("timestamp")
        )
    }
}

@Parcelize
data class Post(
    val title: String,
    val content: String,
    val nickname: String,
    val timestamp: String,
    val comments: MutableList<Comment> = mutableListOf()
) : Parcelable {
    fun toJson(): JSONObject = JSONObject().apply {
        put("title", title)
        put("content", content)
        put("nickname", nickname)
        put("timestamp", timestamp)
        val arr = JSONArray()
        comments.forEach { arr.put(it.toJson()) }
        put("comments", arr)
    }
    companion object {
        fun fromJson(obj: JSONObject): Post = Post(
            title = obj.getString("title"),
            content = obj.getString("content"),
            nickname = obj.getString("nickname"),
            timestamp = obj.getString("timestamp"),
            comments = mutableListOf<Comment>().apply {
                val arr = obj.getJSONArray("comments")
                for (i in 0 until arr.length()) {
                    add(Comment.fromJson(arr.getJSONObject(i)))
                }
            }
        )
    }
}
