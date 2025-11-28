package com.example.artisana.auth.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.artisana.R
import com.example.artisana.auth.viewmodels.RegisterViewModel
import com.example.artisana.core.navigation.Screen

@Composable
fun CreatePasswordScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Handle successful registration
    LaunchedEffect(uiState.isRegistrationSuccessful) {
        if (uiState.isRegistrationSuccessful) {
            navController.navigate(Screen.SuccessAccount.route)
            viewModel.resetRegistrationState()
        }
    }

    // Show error message if any
    if (uiState.errorMessage.isNotEmpty()) {
        LaunchedEffect(uiState.errorMessage) {
            kotlinx.coroutines.delay(3000)
            viewModel.clearErrorMessage()
        }
    }

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
                .clip(CircleShape),
            enabled = !uiState.isLoading
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
            text = "Enfin, créez votre mot de passe",
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
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

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
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
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
                    focusedBorderColor = if (uiState.passwordError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    unfocusedBorderColor = if (uiState.passwordError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                isError = uiState.passwordError.isNotEmpty(),
                enabled = !uiState.isLoading
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(start = 8.dp, top = 4.dp)
            ) {
                if (uiState.passwordError.isNotEmpty()) {
                    Text(
                        text = uiState.passwordError,
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
                value = uiState.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
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
                    focusedBorderColor = if (uiState.confirmPasswordError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    unfocusedBorderColor = if (uiState.confirmPasswordError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                isError = uiState.confirmPasswordError.isNotEmpty(),
                enabled = !uiState.isLoading
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(start = 8.dp, top = 4.dp)
            ) {
                if (uiState.confirmPasswordError.isNotEmpty()) {
                    Text(
                        text = uiState.confirmPasswordError,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 10.sp,
                        lineHeight = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.onRegisterClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "S'inscrire",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // Error message display
        if (uiState.errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = uiState.errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}