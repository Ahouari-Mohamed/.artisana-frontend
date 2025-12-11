package com.example.artisana.auth.ui.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.artisana.R
import com.example.artisana.auth.viewmodels.RegisterViewModel
import com.example.artisana.auth.viewmodels.RegistrationStep
import com.example.artisana.core.navigation.Screen

@Composable
fun RegisterScreen(
    navController: NavHostController
) {
    val registerBackStackEntry = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry(Screen.Register.route)
    }
    val viewModel: RegisterViewModel = viewModel(registerBackStackEntry)

    val uiState by viewModel.uiState.collectAsState()
    // Navigate to CreatePassword screen when validation passes
    LaunchedEffect(uiState.registrationStep) {
        if (uiState.registrationStep == RegistrationStep.PASSWORD) {
            navController.navigate(Screen.CreatePassword.route)
            viewModel.goBackToInfoStep()
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
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Logo
        Icon(
            painterResource(R.drawable.ic_artisana),
            contentDescription = "Logo",
            modifier = Modifier
                .width(140.dp)
                .height(100.dp),
            tint = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Présente-toi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Nom",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Prénom",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = { viewModel.onNameChange(it) },
                    placeholder = {
                        Text(
                            "Nom",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (uiState.nameError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                        unfocusedBorderColor = if (uiState.nameError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    isError = uiState.nameError.isNotEmpty(),
                    enabled = !uiState.isLoading
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .padding(start = 4.dp, top = 2.dp)
                ) {
                    if (uiState.nameError.isNotEmpty()) {
                        Text(
                            text = uiState.nameError,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.error,
                            lineHeight = 12.sp
                        )
                    }
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = uiState.prenom,
                    onValueChange = { viewModel.onPrenomChange(it) },
                    placeholder = {
                        Text(
                            "Prénom",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (uiState.prenomError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                        unfocusedBorderColor = if (uiState.prenomError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    isError = uiState.prenomError.isNotEmpty(),
                    enabled = !uiState.isLoading
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .padding(start = 4.dp, top = 2.dp)
                ) {
                    if (uiState.prenomError.isNotEmpty()) {
                        Text(
                            text = uiState.prenomError,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.error,
                            lineHeight = 12.sp
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Email",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
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
                    focusedBorderColor = if (uiState.emailError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    unfocusedBorderColor = if (uiState.emailError.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                isError = uiState.emailError.isNotEmpty(),
                enabled = !uiState.isLoading
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(start = 8.dp, top = 4.dp)
            ) {
                if (uiState.emailError.isNotEmpty()) {
                    Text(
                        text = uiState.emailError,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.error,
                        lineHeight = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Checkbox(
                checked = uiState.termsAccepted,
                onCheckedChange = { viewModel.onTermsAcceptedChange(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ),
                enabled = !uiState.isLoading
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append("J\'ai lu et j\'accepte ")
                    }
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append("les conditions d\'utilisation")
                    }
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(" et ")
                    }
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append("la politique de confidentialité.")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 14.sp,
                textAlign = TextAlign.Start
            )
        }
        if (!uiState.termsAccepted) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Veuillez accepter les conditions d\'utilisation",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.error,
                    lineHeight = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Register button
        Button(
            onClick = { viewModel.onNextStepClick() },
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
                Text("S\'inscrire", fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary)
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

        Spacer(modifier = Modifier.weight(.8f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
            Text(
                text = "Ou",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                fontSize = 14.sp
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Vous avez déjà un compte? ",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Se Connecter",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        enabled = !uiState.isLoading
                    ) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}
