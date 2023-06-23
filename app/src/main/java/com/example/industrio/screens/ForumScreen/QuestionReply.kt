package com.example.industrio.screens.ForumScreen

data class Question(
    val id: String,
    val category: List<String>,
    val title: String,
    val description: String,
    val userId: String,
    val timestamp: Long
) {
    constructor() : this("", listOf(), "", "", "", 0L)
}

data class Reply(
    val id: String,
    val questionId: String,
    val text: String,
    val userId: String,
    val timestamp: Long
){
    constructor() : this("", "", "", "", 0L)
}







