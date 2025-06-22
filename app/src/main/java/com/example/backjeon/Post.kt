package com.example.backjeon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Post(
    val title: String,
    val content: String,
    val nickname: String,
    val timestamp: String,
    val comments: MutableList<Comment> = mutableListOf()
)

@Parcelize
data class Comment(
    val nickname: String,
    val content: String,
    val timestamp: String
) : Parcelable
