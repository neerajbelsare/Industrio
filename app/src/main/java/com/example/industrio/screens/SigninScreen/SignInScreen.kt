package com.example.industrio.screens.SigninScreen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.industrio.R
import com.example.industrio.Utils.LoadingState
import com.example.industrio.navigation.AuthScreen
import com.example.industrio.navigation.nav_graph.Graph
import com.example.industrio.ui.theme.MainColor
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

    Spacer(modifier = Modifier
        .height(30.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Industrio Icon",
            modifier = Modifier.size(110.dp).align(CenterHorizontally).padding(top = 30.dp)
        )

        Text(
            text = "You must Sign In to Continue",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp, top = 25.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = "Please enter your username and password to sign in",
            textAlign = TextAlign.Center,
            color = Color(0xFF919191)
        )

        Text(
            text = "Email Address",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF818181),
            modifier = Modifier.padding(top = 40.dp, bottom = 10.dp),
        )
        OutlinedTextField(
            value = signInViewModel.email,
            onValueChange = { signInViewModel.email = it },
            label = { Text("Email address",
                color = Color(0xFFAFAFAF),) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_mail),
                    contentDescription = "Email Icon",
                    tint = Color(0xFFAFAFAF)
                )
            },
            modifier = Modifier.fillMaxWidth(),

            colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MainColor,
            unfocusedBorderColor = Color(0xFFC7C7C7)
        ),
        shape = RoundedCornerShape(15.dp)
        )

        Text(
            text = "Password",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF919191),
            modifier = Modifier.padding(top = 20.dp)
        )
        OutlinedTextField(
            value = signInViewModel.password,
            onValueChange = { signInViewModel.password = it },
            label = { Text("Password",
                    color = Color(0xFFAFAFAF),) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = "Password Icon",
                    tint = Color(0xFFAFAFAF)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MainColor,
            unfocusedBorderColor = Color(0xFFC7C7C7)
            ),
            shape = RoundedCornerShape(15.dp)
        )

        TextButton(onClick = {}) {
            Text(text = "Forgot Password?",
            color = MainColor,
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            textAlign = TextAlign.Right)
        }

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
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
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
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier
            .height(1.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth()
                .border(width = 1.dp, color = Color(0xFFC7C7C7), shape = RoundedCornerShape(8.dp)),
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
                contentDescription = "Google Icon",
                modifier = Modifier.width(28.dp)
            )

            Text(text = "   Log In with Google",
                textAlign = TextAlign.Center,
            fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier
            .height(15.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth()
                .border(width = 1.dp, color = Color(0xFFC7C7C7), shape = RoundedCornerShape(8.dp)),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            onClick = {
            }
        ) {
            Image(
                painterResource(id = R.drawable.facebook_icon),
                contentDescription = "Facebook Icon",
                modifier = Modifier.width(28.dp)
            )
            Text(text = "   Log In with Facebook",
                textAlign = TextAlign.Center,
                fontSize = 15.sp)
        }

        Spacer(modifier = Modifier
            .height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                color = Color(0xFF919191),
                text = "Don't have an account?",
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