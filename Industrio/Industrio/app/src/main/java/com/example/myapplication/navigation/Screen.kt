package com.example.myapplication.navigation

import com.example.myapplication.core.Constants.FORGOT_PASSWORD_SCREEN
import com.example.myapplication.core.Constants.VERIFY_EMAIL_SCREEN

sealed class Screen(val route: String) {
    object ForgotPasswordScreen: Screen(FORGOT_PASSWORD_SCREEN)
    object VerifyEmailScreen: Screen(VERIFY_EMAIL_SCREEN)
}