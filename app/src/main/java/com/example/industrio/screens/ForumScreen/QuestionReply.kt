package com.example.industrio.screens.ForumScreen

data class Question(
    val id: String,
    val category: String,
    val text: String,
    val userId: String,
    val timestamp: Long
) {
    constructor() : this("", "", "", "", 0L)
}

data class Reply(
    val id: String,
    val questionId: String,
    val text: String,
    val userId: String,
    val timestamp: Long
)







