package com.example.quote.ui.auth


import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.quoteapp.ui.auth.SignupScreen

enum class AuthScreen {
    LOGIN,
    SIGNUP,
    FORGOT_PASSWORD
}

@Composable
fun AuthNavigator(
    onAuthSuccess: () -> Unit
) {
    var currentScreen by remember { mutableStateOf(AuthScreen.LOGIN) }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            ) togetherWith slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        label = "auth_navigation"
    ) { screen ->
        when (screen) {
            AuthScreen.LOGIN -> LoginScreen(
                onSignupClick = { currentScreen = AuthScreen.SIGNUP },
                onForgotClick = { currentScreen = AuthScreen.FORGOT_PASSWORD },
                onLoginSuccess = onAuthSuccess
            )

            AuthScreen.SIGNUP -> SignupScreen(
                onLoginClick = { currentScreen = AuthScreen.LOGIN },
                onSignupSuccess = onAuthSuccess
            )

            AuthScreen.FORGOT_PASSWORD -> ForgotPasswordScreen(
                onBackToLogin = { currentScreen = AuthScreen.LOGIN }
            )
        }
    }
}