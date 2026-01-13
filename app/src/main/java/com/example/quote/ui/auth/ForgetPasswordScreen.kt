package com.example.quote.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quote.AppColors
import com.example.quote.ui.ElegantWavesBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var emailSent by remember { mutableStateOf(false) }

    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ElegantWavesBackground()


        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Back Button - Top Left
            IconButton(
                onClick = onBackToLogin,
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppColors.Surface)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back to login",
                    tint = AppColors.Primary
                )
            }

            // Main Content
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 }),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!emailSent) {
                        // Reset Password UI
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            AppColors.Gradient3,
                                            AppColors.Gradient1
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "🔐",
                                fontSize = 36.sp
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        Text(
                            "Reset Password",
                            fontSize = 28.sp,
                            color = AppColors.TextPrimary
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "We'll send you a reset link",
                            fontSize = 14.sp,
                            color = AppColors.TextSecondary,
                            textAlign = TextAlign.Center
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
                                        "Enter your email",
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

                        Spacer(Modifier.height(32.dp))

                        // Send Reset Link Button
                        GradientButton(
                            text = if (isLoading) "Sending..." else "Send Reset Link",
                            onClick = {
                                isLoading = true
                                kotlinx.coroutines.GlobalScope.launch {
                                    delay(1500)
                                    isLoading = false
                                    emailSent = true
                                }
                            },
                            enabled = !isLoading && email.isNotBlank(),
                            modifier = Modifier.fillMaxWidth()
                        )

                    } else {
                        // Success UI
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF10B981),
                                            Color(0xFF059669)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "✓",
                                fontSize = 56.sp,
                                color = Color.White
                            )
                        }

                        Spacer(Modifier.height(32.dp))

                        Text(
                            "Email Sent!",
                            fontSize = 28.sp,
                            color = AppColors.TextPrimary
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            "Check your inbox for password reset instructions.",
                            fontSize = 14.sp,
                            color = AppColors.TextSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            email,
                            fontSize = 14.sp,
                            color = AppColors.Primary
                        )

                        Spacer(Modifier.height(40.dp))

                        OutlinedButton(
                            onClick = onBackToLogin,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = AppColors.Primary
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        AppColors.Gradient1,
                                        AppColors.Gradient2
                                    )
                                )
                            )
                        ) {
                            Text(
                                "Back to Login",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}