package com.example.industrio.screens.SignupScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.industrio.Utils.LoadingState
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel : ViewModel() {

    private lateinit var firestore: FirebaseFirestore

    var name by mutableStateOf("")
    var phone by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var isError by mutableStateOf(false)
    var isEmpty by mutableStateOf(false)
    var isSignedUp by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    val loadingState = MutableStateFlow(LoadingState.IDLE)
    fun signUp(user: NewUser) {
        isLoading = true
        isError = false

        val auth = Firebase.auth
        val db = Firebase.firestore
        var username = generateAndStoreUsername(user.name)

        if(email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnSuccessListener {
                    val newUser = hashMapOf(
                        "name" to user.name,
                        "email" to user.email,
                        "phone" to user.phone,
                        "password" to user.password,
                        "username" to username,
                        "isTechnician" to false,
                        "isCompany" to false,
                        "imageUrl" to "https://drive.google.com/file/d/1jaMTdkE-IxTEHRVsHaDwUNTEHm-U8xVw/view?usp=sharing"
                    )

                    db.collection("users")
                        .document(it.user!!.uid)
                        .set(newUser)
                        .addOnSuccessListener {
                            isLoading = false
                             isSignedUp = true
                        }
                        .addOnFailureListener {
                            errorMessage = "There was an error! Please try again!"
                            isLoading = false
                            isError = true

                        }
                }
                .addOnFailureListener { exception ->
                    if (exception is FirebaseAuthUserCollisionException) {
                        val errorCode = exception.errorCode
                        if (errorCode == "ERROR_EMAIL_ALREADY_IN_USE") {
                            errorMessage = "The user with the provided email already exists."
                        }
                        errorMessage = "There was an error while signing up! Please try again!"
                        isLoading = false
                        isError = true
                    }
                }
            }
            else{
                isEmpty = true
            }
    }

    fun generateUsername(name: String): String {
        val prefix = name.lowercase().replace(" ", "")
        val suffix = (10000..99999).random()
        return "$prefix$suffix"
    }

    fun generateAndStoreUsername(name: String): String {
        firestore = FirebaseFirestore.getInstance()

        val username = generateUsername(name)
        val usersRef = firestore.collection("users")
        val query = usersRef.whereEqualTo("username", username)

        query.get().addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                return@addOnSuccessListener
            } else {
                generateAndStoreUsername(name)
            }
        }
        return username
    }

    fun signWithGoogleCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.LOADED)

            isSignedUp = true
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }
}