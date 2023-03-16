package com.example.industrio.screens.SigninScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.industrio.Utils.LoadingState
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var isError by mutableStateOf(false)
    var isEmpty by mutableStateOf(false)
    var isLoggedIn by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    val loadingState = MutableStateFlow(LoadingState.IDLE)

    private lateinit var user: FirebaseAuth

    fun signIn() {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            user.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    isLoading = false
                    isLoggedIn = true
                }
                else {
                    isError = true
                    errorMessage = "The email or password is incorrect."
                }
            }

        } else {
            isError = true
            errorMessage = "Please fill out all the fields."
            isEmpty = true
        }
    }

    fun signWithGoogleCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.LOADED)

            isLoggedIn = true
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }
}