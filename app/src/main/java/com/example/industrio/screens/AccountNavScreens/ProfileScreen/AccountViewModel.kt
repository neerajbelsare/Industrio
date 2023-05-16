package com.example.industrio.screens.AccountNavScreens.ProfileScreen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.industrio.screens.AccountNavScreens.RetrieveUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

class AccountViewModel : ViewModel() {
    private lateinit var firestore: FirebaseFirestore

    var username by mutableStateOf("")
    var name by mutableStateOf("")
    var phone by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isTechnician by mutableStateOf(false)
    var isCompany by mutableStateOf(false)
    var imageUrl by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var isUpdated by mutableStateOf(false)

    private val fdb = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userDetails = MutableStateFlow<RetrieveUser?>(null)

    init {
        isLoading = true
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            getUserDetails()
        }
    }

    private fun getUserDetails() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userDocument = fdb.collection("users").document(userId)

            viewModelScope.launch {
                userDocument.addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        // Handle error
                    } else {
                        val userDetails = snapshot?.toObject(RetrieveUser::class.java)
                        _userDetails.value = userDetails

                        if (userDetails != null) {
                            username = userDetails.username
                            name = userDetails.name
                            email = userDetails.email
                            phone = userDetails.phone
                            password = userDetails.password
                            isTechnician = userDetails.isTechnician
                            isCompany = userDetails.isCompany

                            imageUrl = userDetails.imageUrl

                            isLoading = false
                        }
                    }
                }
            }
        }
    }


    fun updateUserDetails() {
        isLoading = true
        val currentUser = FirebaseAuth.getInstance().currentUser
        val firebase = FirebaseFirestore.getInstance()
        val userDocRef = firebase.collection("users").document(currentUser!!.uid)

        val updatedDetails = mapOf(
            "name" to name,
            "email" to email,
            "phone" to phone
        )
        userDocRef.update(updatedDetails)
            .addOnSuccessListener {
                isLoading = false
                isUpdated = true
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    fun uploadImageToStorage(imageUri: Uri): Task<Uri> {
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}")
        return imageRef.putFile(imageUri).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            imageRef.downloadUrl
        }
    }

    fun saveImageUrlToFirestore(imageUrl: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val db = Firebase.firestore
        user?.uid?.let { uid ->
            db.collection("users")
                .document(uid)
                .update("imageUrl", imageUrl)
        }
    }
}