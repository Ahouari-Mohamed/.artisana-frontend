package com.example.artisana.auth.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.artisana.core.navigation.Screen

fun isValidPassword(password: String): Boolean {
    val hasMinLength = password.length >= 8
    val hasLetter = password.any { it.isLetter() }
    val hasDigit = password.any { it.isDigit() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() }

    return hasMinLength && hasLetter && hasDigit && hasSpecialChar
}

@Composable
fun CreatePasswordScreen(navController: NavHostController) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    val textColor = Color(0xFFB08D5B)
    val darkBrownColor = Color(0xFFB08D5B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 100.dp),
        horizontalAlignment = Alignment.Start
    ) {

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .size(48.dp)
                .border(
                    width = 1.dp,
                    color = textColor, // or your desired color
                    shape = CircleShape
                )
                .clip(CircleShape) // ensures the ripple and background stay circular
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Retour",
                tint = textColor,
                modifier = Modifier.size(24.dp) // smaller so it fits nicely inside
            )
        }


        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = "Enfin, créez votre mot de passe",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Le mot de passe nécessite un minimum de 8 caractères et contient un mélange de lettres , de chiffres et de symbols.",
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Password label
        Text(
            text = "Mot de passe",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = ""
            },
            placeholder = {
                Text(
                    text = "Votre mot de passe",
                    color = Color.Gray.copy(alpha = 0.5f),
                    fontSize = 14.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (passwordError.isEmpty()) Color.Black else Color.Red,
                unfocusedBorderColor = if (passwordError.isEmpty()) Color.Black else Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            isError = passwordError.isNotEmpty()
        )

        if (passwordError.isNotEmpty()) {
            Text(
                text = passwordError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Confirm password label
        Text(
            text = "Répéter le mot de passe",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm password field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = ""
            },
            placeholder = {
                Text(
                    text = "Répéter votre mot de passe",
                    color = Color.Gray.copy(alpha = 0.5f),
                    fontSize = 14.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (confirmPasswordError.isEmpty()) Color.Black else Color.Red,
                unfocusedBorderColor = if (confirmPasswordError.isEmpty()) Color.Black else Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            isError = confirmPasswordError.isNotEmpty()
        )

        if (confirmPasswordError.isNotEmpty()) {
            Text(
                text = confirmPasswordError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                var valid = true

                if (password.isBlank()) {
                    passwordError = "Veuillez entrer un mot de passe"
                    valid = false
                } else if (!isValidPassword(password)) {
                    passwordError = "Le mot de passe doit contenir au moins 8 caractères, des lettres, des chiffres et des symboles"
                    valid = false
                }

                if (confirmPassword.isBlank()) {
                    confirmPasswordError = "Veuillez confirmer votre mot de passe"
                    valid = false
                } else if (password != confirmPassword) {
                    confirmPasswordError = "Les mots de passe ne correspondent pas"
                    valid = false
                }

                if (valid) {
                    // Navigate to next screen or complete registration
                    navController.navigate(Screen.Success.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = darkBrownColor)
        ) {
            Text(
                text = "S'inscrire",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreatePasswordScreen() {
    val navController = rememberNavController()
    CreatePasswordScreen(navController = navController)
}