package com.example.industrio.screens.SigninScreen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.industrio.R
import com.example.industrio.Utils.LoadingState
import com.example.industrio.navigation.AuthScreen
import com.example.industrio.navigation.nav_graph.Graph
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun SignInScreen(navController: NavController, signInViewModel: SignInViewModel = viewModel()) {
    val allInputsFilled = signInViewModel.email.isNotBlank() && signInViewModel.password.isNotBlank()

    val status by signInViewModel.loadingState.collectAsState()
    val context = LocalContext.current
    val token = stringResource(R.string.default_web_client_id)

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            signInViewModel.signWithGoogleCredential(credential)
        } catch (e: ApiException) {
            Log.w("TAG", "Google sign in failed", e)
        }
    }

    Image(
    painter = painterResource(id = R.drawable.artboard_1),
    contentDescription = "My Image",
    modifier = Modifier
        .clip(RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp))
    )

    Spacer(modifier = Modifier
        .height(30.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "Sign In",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = signInViewModel.email,
            onValueChange = { signInViewModel.email = it },
            label = { Text("Email address") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = signInViewModel.password,
            onValueChange = { signInViewModel.password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
        )
        if (signInViewModel.isLoading) {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = allInputsFilled
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(30.dp),
                    color = Color.White,
                    strokeWidth = 3.dp
                )
            }
        } else {
            Button(
                onClick = { signInViewModel.signIn() },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = allInputsFilled
            ) {
                Text("Sign In")
            }
        }

        if (signInViewModel.isError) {
            Text(
                text = signInViewModel.errorMessage
            )
        }

        if(signInViewModel.isLoggedIn) {
            LaunchedEffect(Unit) {
                navController.popBackStack()
                navController.navigate(Graph.HOME)
            }
        }

        Spacer(modifier = Modifier
            .height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Or",
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier
            .height(1.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()) {
            Button(
                modifier = Modifier,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                onClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                }
            ) {
                Image(
                    painterResource(id = R.drawable.google_icon),
                    contentDescription = "Google Icon"
                )
            }

            Button(
                modifier = Modifier,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                onClick = {
                }
            ) {
                Image(
                    painterResource(id = R.drawable.facebook_icon),
                    contentDescription = "Facebook Icon"
                )
            }
        }

        Spacer(modifier = Modifier
            .height(1.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New to Industrio?",
            )

            TextButton(onClick = {navController.navigate(AuthScreen.SignUpScreen.route)}) {
                Text(text = "Sign Up")
            }
        }
    }

    when (status.status) {
        LoadingState.Status.FAILED -> {
            Toast.makeText(context, status.msg ?: "Error", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
}