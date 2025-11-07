package com.example.artisana.auth.ui.login

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.artisana.core.navigation.Screen

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    var login by remember { mutableStateOf(false) }

    val brownColor = Color(0xFFC4A574)
    val darkBrownColor = Color(0xFFB08D5B)
    val textColor = Color(0xFFB08D5B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        // Logo with dot
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Canvas(
                modifier = Modifier.size(12.dp)
            ) {
                drawCircle(
                    color = brownColor,
                    radius = size.minDimension / 2
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Artisana",
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                color = brownColor
            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        // Title
        Text(
            text = "Content de te revoir!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email field
        Text(
            text = "Email",
            fontSize = 14.sp,
            color = Color.Black,
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
                    color = Color.Gray.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (emailError.isEmpty()) Color.Black else Color.Red,
                unfocusedBorderColor = if (emailError.isEmpty()) Color.Black else Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
        )

        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Password field
        Text(
            text = "Mot de passe",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError =""
            },
            placeholder = {
                Text(
                    text = "Au moins 8 caractères",
                    color = Color.Gray.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (passwordError.isEmpty()) Color.Black else Color.Red,
                unfocusedBorderColor = if (passwordError.isEmpty()) Color.Black else Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
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

        Spacer(modifier = Modifier.height(12.dp))

        // Forgot password
        Text(
            text = "Mot de passe oublié?",
            textDecoration = TextDecoration.Underline,
            fontSize = 14.sp,
            color = textColor,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ){
                    navController.navigate(Screen.ForgotPassword.route)
                }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Login button
        Button(
            onClick = {
                if (email.isBlank()) {
                    emailError = "Veuillez entrer votre email"
                    login = false
                } else {
                    emailError = ""
                    login = true
                }
                if (password.isBlank()) {
                    passwordError = "Veuillez entrer votre mot de passe"
                    login = false
                } else {
                    emailError = ""
                    login = true
                }

                if (login){
                    navController.navigate(Screen.Home.route) {
                        // Pop up to the root of the entire navigation graph.
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }

                        // Ensure there's only one copy of the Home screen on the stack
                         launchSingleTop = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = darkBrownColor
            )
        ) {
            Text(
                text = "Se connecter",
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Divider with "Ou"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = DividerDefaults.Thickness, color = Color.Gray.copy(alpha = 0.3f)
            )
            Text(
                text = "Ou",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Gray,
                fontSize = 14.sp
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = DividerDefaults.Thickness, color = Color.Gray.copy(alpha = 0.3f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign up text
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Vous n'avez pas de compte? ",
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = "S'inscrire",
                fontSize = 14.sp,
                color = textColor,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ){
                        navController.navigate(Screen.Register.route)
                    }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
