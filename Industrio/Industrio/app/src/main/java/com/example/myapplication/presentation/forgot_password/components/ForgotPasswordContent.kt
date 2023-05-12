package com.example.myapplication.presentation.forgot_password.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.components.EmailField
import com.example.myapplication.components.SmallSpacer
import com.example.myapplication.core.Constants.NO_VALUE
import com.example.myapplication.core.Constants.RESET_PASSWORD
import com.example.myapplicationjetpack.ui.theme.*

@Composable
fun ForgotPasswordContent(
    padding: PaddingValues,
    sendPasswordResetEmail: (email: String) -> Unit,
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(NO_VALUE)) }



    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "FORGOT YOUR PASSWORD?",
            fontFamily = Poppins,
            color = SecondaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp
        )
        Image(
            painter = painterResource(id = R.drawable.ic_forgot_password_illustration),
            contentDescription = "",
            modifier = Modifier.size(240.dp)
        )
        Card(
            backgroundColor = Color.White,
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Column(
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Enter your registered email below to receive password reset instruction",
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp
                )
                TextField(
                    value = email, onValueChange = { newValue ->
                        email = newValue },
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = PrimaryColor,
                        backgroundColor = BackgroundColor,
                        cursorColor = PrimaryColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = InputBoxShape.medium,
                    singleLine = true,
                    leadingIcon = {
                        Row(
                            modifier = Modifier.padding(start = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_email_outline),
                                contentDescription = "",
                                tint = PrimaryColor,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(6.dp)
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(24.dp)
                                    .background(PrimaryColor)
                            )
                        }
                    },
                    placeholder = {
                        Text(text = "Email Address", color = Color.Gray)
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins
                    )
                )


                Button(
                    onClick = {
                        sendPasswordResetEmail(email.text)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = PrimaryColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp),
                    contentPadding = PaddingValues(vertical = 14.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    shape = Shapes.medium
                ) {
                    Text(
                        text = "Send Reset Link",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


            }

        }
        TextButton(onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Text(text = "Remember password? Login",
                fontFamily = Poppins,
                color= SecondaryColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }

}