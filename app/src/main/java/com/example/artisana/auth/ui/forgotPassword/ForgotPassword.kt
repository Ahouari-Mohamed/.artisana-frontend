package com.example.artisana.auth.ui.forgotPassword

import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.artisana.R
import com.example.artisana.core.navigation.Screen

fun isValidPassword(password: String): Boolean {
    val hasMinLength = password.length >= 8
    val hasLetter = password.any { it.isLetter() }
    val hasDigit = password.any { it.isDigit() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() }

    return hasMinLength && hasLetter && hasDigit && hasSpecialChar
}

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 100.dp, bottom = 60.dp),
        horizontalAlignment = Alignment.Start
    ) {

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(48.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clip(CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_return),
                contentDescription = "Retour",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = "Réinitialiser le mot de passe",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Le mot de passe nécessite un minimum de 8 caractères et contient un mélange de lettres, de chiffres et de symboles.",
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Email field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Email",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = ""
                },
                placeholder = {
                    Text(
                        text = "YourAdresse@example.com",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (emailError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    unfocusedBorderColor = if (emailError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                isError = emailError.isNotEmpty()
            )

            // Fixed height error space
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(start = 8.dp, top = 4.dp)
            ) {
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.error,
                        lineHeight = 14.sp
                    )
                }
            }
        }

        // Password field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Mot de passe",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = ""
                },
                placeholder = {
                    Text(
                        text = "Votre mot de passe",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (passwordError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    unfocusedBorderColor = if (passwordError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                isError = passwordError.isNotEmpty()
            )

            // Fixed height error space
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(start = 8.dp, top = 4.dp)
            ) {
                if (passwordError.isNotEmpty()) {
                    Text(
                        text = passwordError,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 10.sp,
                        lineHeight = 13.sp
                    )
                }
            }
        }

        // Confirm password field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Répéter le mot de passe",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = ""
                },
                placeholder = {
                    Text(
                        text = "Répéter votre mot de passe",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (confirmPasswordError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    unfocusedBorderColor = if (confirmPasswordError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                isError = confirmPasswordError.isNotEmpty()
            )

            // Fixed height error space
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(start = 8.dp, top = 4.dp)
            ) {
                if (confirmPasswordError.isNotEmpty()) {
                    Text(
                        text = confirmPasswordError,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 10.sp,
                        lineHeight = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                var valid = true

//                if (email.isBlank()) {
//                    emailError = "Veuillez entrer votre email"
//                    valid = false
//                }
//
//                if (password.isBlank()) {
//                    passwordError = "Veuillez entrer un mot de passe"
//                    valid = false
//                } else if (!isValidPassword(password)) {
//                    passwordError = "8 caractères min, lettres, chiffres et symboles"
//                    valid = false
//                }
//
//                if (confirmPassword.isBlank()) {
//                    confirmPasswordError = "Confirmez votre mot de passe"
//                    valid = false
//                } else if (password != confirmPassword) {
//                    confirmPasswordError = "Les mots de passe ne correspondent pas"
//                    valid = false
//                }

                if (valid) {
                    navController.navigate(Screen.SuccessPass.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Réinitialiser",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}