package com.example.artisana.auth.ui.register

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.artisana.core.navigation.Screen

// Email validation function
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun RegisterScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var prenomError by remember { mutableStateOf("") }

    val brownColor = Color(0xFFC4A574)
    val darkBrownColor = Color(0xFFB08D5B)
    val textColor = Color(0xFFB08D5B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Logo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Canvas(modifier = Modifier.size(12.dp)) {
                drawCircle(color = brownColor, radius = size.minDimension / 2)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Artisana",
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                color = brownColor,
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Présente-toi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = textColor,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Pour finaliser ton inscription, nous avons besoin de quelques informations sur toi.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = textColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            textAlign = TextAlign.Start,
            lineHeight = 15.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Column(modifier = Modifier.weight(1f)){
                Text(
                    text = "Nom",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(modifier = Modifier.weight(1f)){
                Text(
                    text = "Prénom",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = ""
                    },
                    placeholder = { Text("Nom",
                        color = Color.Gray.copy(alpha = 0.5f))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (nameError.isEmpty()) Color.Black else Color.Red,
                        unfocusedBorderColor = if (nameError.isEmpty()) Color.Black else Color.Red
                    ),
                    isError = nameError.isNotEmpty()
                )

                if (nameError.isNotEmpty()) {
                    Text(
                        text = nameError,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 4.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = prenom,
                    onValueChange = {
                        prenom = it
                        prenomError = ""
                    },
                    placeholder = { Text("Prénom",
                        color = Color.Gray.copy(alpha = 0.5f))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (prenomError.isEmpty()) Color.Black else Color.Red,
                        unfocusedBorderColor = if (prenomError.isEmpty()) Color.Black else Color.Red
                    ),
                    isError = prenomError.isNotEmpty()
                )

                if (prenomError.isNotEmpty()) {
                    Text(
                        text = prenomError,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 4.dp)
                    )
                }
            }
        }

        Text(
            text = "Email",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        // email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = ""
            },
            placeholder = {
                Text(
                    text = "YourAdresse@example.com",
                    color = Color.Gray.copy(alpha = 0.5f)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (emailError.isEmpty()) Color.Black else Color.Red,
                unfocusedBorderColor = if (emailError.isEmpty()) Color.Black else Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            isError = emailError.isNotEmpty()
        )

        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp)
            )
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(vertical = 8.dp)
        ){
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Normal)) {
                        append("J'ai lu et j'accepte ")
                    }
                    withStyle(SpanStyle(color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Normal)) {
                        append("les conditions d'utilisation")
                    }
                    withStyle(SpanStyle(color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Normal)) {
                        append(" et ")
                    }
                    withStyle(SpanStyle(color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Normal)) {
                        append("la politique de confidentialité.")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 14.sp,
                textAlign = TextAlign.Start
            )
        }


        // Register button
        Button(
            onClick = {
                var valid = true

                if (name.isBlank()) {
                    nameError = "Veuillez entrer votre nom"
                    valid = false
                }
                if (prenom.isBlank()) {
                    prenomError = "Veuillez entrer votre prénom"
                    valid = false
                }
                if (email.isBlank()) {
                    emailError = "Veuillez entrer votre email"
                    valid = false
                } else if (!isValidEmail(email)) {
                    emailError = "Format d'email invalide"
                    valid = false
                }
                if (!checked) {
                    valid = false
                }

                if (valid) {
                    navController.navigate(Screen.CreatePassword.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = darkBrownColor)
        ) {
            Text("S'inscrire", fontSize = 16.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.weight(.8f))

        // Go back to login
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

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Vous avez déjà un compte?",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = " Se Connecter",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = textColor,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ){
                        navController.navigate(Screen.Login.route)
                    }
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLineHeightExample() {
    val navController = rememberNavController()
    RegisterScreen(navController = navController)
}