package com.example.industrio.screens.ForumScreen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.UUID

class ForumViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val questionsCollection = firestore.collection("questions")
    private val repliesCollection = firestore.collection("replies")

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    private val _replies = MutableLiveData<List<Reply>>()
    val replies: LiveData<List<Reply>> get() = _replies

    init {
        fetchQuestions()
    }

    fun fetchQuestions() {
        questionsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(5)
            .get()
            .addOnSuccessListener { documents ->
                val questionList = mutableListOf<Question>()
                for (document in documents) {
                    val question = document.toObject(Question::class.java)
                    questionList.add(question)
                }
                _questions.value = questionList
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    fun postQuestion(category: List<String>, title: String, description: String, userId: String) {
        val question = Question(
            id = UUID.randomUUID().toString(),
            category = category,
            title = title,
            description = description,
            userId = userId,
            timestamp = System.currentTimeMillis()
        )

        questionsCollection
            .document(question.id)
            .set(question)
            .addOnSuccessListener {

            }
            .addOnFailureListener { exception ->

            }
    }

    fun fetchReplies(questionId: String) {
        repliesCollection
            .whereEqualTo("questionId", questionId)
            .get()
            .addOnSuccessListener { documents ->
                val replyList = mutableListOf<Reply>()
                for (document in documents) {
                    val reply = document.toObject(Reply::class.java)
                    replyList.add(reply)
                }
                _replies.value = replyList
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    fun postReply(questionId: String, text: String, userId: String) {
        val reply = Reply(
            id = UUID.randomUUID().toString(),
            questionId = questionId,
            text = text,
            userId = userId,
            timestamp = System.currentTimeMillis()
        )

        repliesCollection
            .document(reply.id)
            .set(reply)
            .addOnSuccessListener {
                // Reply posted successfully
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }
}


