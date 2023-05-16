package com.example.industrio.screens.HomeScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.industrio.screens.AccountNavScreens.RetrieveUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel() : ViewModel() {
    var isLoading by mutableStateOf(false)
    var imageUrl by mutableStateOf("")

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userDetails = MutableStateFlow<RetrieveUser?>(null)
    val userDetails = _userDetails.asStateFlow()

    init {
        loadUserDetails()
    }

    private fun loadUserDetails() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userDocument = firestore.collection("users").document(userId)

            viewModelScope.launch {
                userDocument.addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        // Handle error
                    } else {
                        val userDetails = snapshot?.toObject(RetrieveUser::class.java)
                        _userDetails.value = userDetails

                        if (userDetails != null) {
                            imageUrl = userDetails.imageUrl
                        }
                    }
                }
            }
        }
    }
}





