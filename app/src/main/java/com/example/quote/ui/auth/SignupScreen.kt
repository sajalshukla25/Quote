package com.example.quoteapp.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quote.AppColors
import com.example.quote.ui.ElegantWavesBackground
import com.example.quote.ui.auth.GradientButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    onLoginClick: () -> Unit,
    onSignupSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
    }

    val passwordsMatch = password == confirmPassword && confirmPassword.isNotEmpty()

    Box(modifier = Modifier.fillMaxSize()) {
        ElegantWavesBackground()

        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                                    AppColors.Gradient2,
                                    AppColors.Gradient3
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "✨",
                        fontSize = 36.sp
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Welcome Text
                Text(
                    "Create Account",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Join QuoteVault today",
                    fontSize = 14.sp,
                    color = AppColors.TextSecondary
                )

                Spacer(Modifier.height(40.dp))

                // Name Field
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(AppColors.Surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = {
                            Text(
                                "Full Name",
                                color = AppColors.TextSecondary.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = AppColors.Primary
                            )
                        },
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

                Spacer(Modifier.height(16.dp))

                // Confirm Password Field
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(AppColors.Surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = {
                            Text(
                                "Confirm Password",
                                color = AppColors.TextSecondary.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = if (passwordsMatch) Color(0xFF10B981) else AppColors.Primary
                            )
                        },
                        trailingIcon = {
                            TextButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Text(
                                    if (confirmPasswordVisible) "Hide" else "Show",
                                    color = AppColors.Primary,
                                    fontSize = 14.sp
                                )
                            }
                        },
                        visualTransformation = if (confirmPasswordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (passwordsMatch)
                                Color(0xFF10B981)
                            else
                                AppColors.Primary,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = AppColors.Surface,
                            unfocusedContainerColor = AppColors.Surface
                        ),
                        singleLine = true,
                        isError = confirmPassword.isNotEmpty() && !passwordsMatch
                    )
                }

                // Password match indicator
                if (confirmPassword.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        if (passwordsMatch) "✓ Passwords match" else "✗ Passwords don't match",
                        fontSize = 12.sp,
                        color = if (passwordsMatch) Color(0xFF10B981) else Color(0xFFEF4444),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(32.dp))

                // Signup Button
                GradientButton(
                    text = if (isLoading) "Creating account..." else "Sign Up",
                    onClick = {
                        isLoading = true
                        kotlinx.coroutines.GlobalScope.launch {
                            delay(1000)
                            onSignupSuccess()
                        }
                    },
                    enabled = !isLoading &&
                            name.isNotBlank() &&
                            email.isNotBlank() &&
                            password.isNotBlank() &&
                            passwordsMatch,
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

                // Login prompt
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Already have an account?",
                        fontSize = 14.sp,
                        color = AppColors.TextSecondary
                    )
                    TextButton(
                        onClick = onLoginClick,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = AppColors.Primary
                        )
                    ) {
                        Text(
                            "Login",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}