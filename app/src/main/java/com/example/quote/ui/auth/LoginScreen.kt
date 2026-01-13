package com.example.quote.ui.auth


import androidx.compose.animation.AnimatedVisibility

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
// Password visibility icons removed - using text instead
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quote.AppColors
import com.example.quote.ui.ElegantWavesBackground
//import com.example.quoteapp.AppColors
//import com.example.quoteapp.ElegantWavesBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onSignupClick: () -> Unit,
    onForgotClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ElegantWavesBackground()

        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo/Icon Section
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    AppColors.Gradient1,
                                    AppColors.Gradient2
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Q",
                        fontSize = 40.sp,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Welcome Text
                Text(
                    "Welcome Back 👋",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Login to continue your journey",
                    fontSize = 14.sp,
                    color = AppColors.TextSecondary
                )

                Spacer(Modifier.height(40.dp))

                // Email Field
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(AppColors.Surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = {
                            Text(
                                "Email",
                                color = AppColors.TextSecondary.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = AppColors.Primary
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.Primary,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = AppColors.Surface,
                            unfocusedContainerColor = AppColors.Surface
                        ),
                        singleLine = true
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Password Field
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(AppColors.Surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = {
                            Text(
                                "Password",
                                color = AppColors.TextSecondary.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = AppColors.Primary
                            )
                        },
                        trailingIcon = {
                            TextButton(onClick = { passwordVisible = !passwordVisible }) {
                                Text(
                                    if (passwordVisible) "Hide" else "Show",
                                    color = AppColors.Primary,
                                    fontSize = 14.sp
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.Primary,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = AppColors.Surface,
                            unfocusedContainerColor = AppColors.Surface
                        ),
                        singleLine = true
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Forgot Password
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(
                        onClick = onForgotClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = AppColors.Primary
                        )
                    ) {
                        Text(
                            "Forgot password?",
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Login Button
                GradientButton(
                    text = if (isLoading) "Logging in..." else "Login",
                    onClick = {
                        isLoading = true
                        // Simulate login delay
                        kotlinx.coroutines.GlobalScope.launch {
                            delay(1000)
                            onLoginSuccess()
                        }
                    },
                    enabled = !isLoading && email.isNotBlank() && password.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                // Divider with text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = AppColors.TextSecondary.copy(alpha = 0.3f)
                    )
                    Text(
                        "or",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = AppColors.TextSecondary,
                        fontSize = 14.sp
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = AppColors.TextSecondary.copy(alpha = 0.3f)
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Sign up prompt
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Don't have an account?",
                        fontSize = 14.sp,
                        color = AppColors.TextSecondary
                    )
                    TextButton(
                        onClick = onSignupClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = AppColors.Primary
                        )
                    ) {
                        Text(
                            "Sign up",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "buttonScale"
    )

    Box(
        modifier = modifier
            .height(56.dp)
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (enabled) {
                    Brush.horizontalGradient(
                        colors = listOf(
                            AppColors.Gradient1,
                            AppColors.Gradient2
                        )
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(
                            AppColors.TextSecondary.copy(alpha = 0.3f),
                            AppColors.TextSecondary.copy(alpha = 0.3f)
                        )
                    )
                }
            )
            .clickable(
                onClick = {
                    if (enabled) {
                        pressed = true
                        onClick()
                    }
                },
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}