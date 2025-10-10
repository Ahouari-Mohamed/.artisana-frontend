package com.example.artisana.splash.ui.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artisana.core.navigation.Screen

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val brownColor = Color(0xFFB4916C)
    val lightBrownColor = Color(0xFFC2B89C)
    val darkBrownColor = Color(0xFFB08D5B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Logo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Canvas(modifier = Modifier.size(12.dp)) {
                drawCircle(color = brownColor)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Artisana",
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                color = brownColor
            )
        }

        Spacer(modifier = Modifier.fillMaxHeight(.25f))

        // Title
        Text(
            text = "Artisanat Authentique",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Chaque pièce est fabriquée à la main par des artisans marocains qualifiés, préservant des traditions séculaires et apportant la chaleur du Maroc dans votre maison.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Features
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(color = lightBrownColor.copy(alpha = 0.3f))
                    }
                    Text(text = "✋", fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Fait main",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(color = lightBrownColor.copy(alpha = 0.3f))
                    }
                    Text(text = "🚚", fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Expédition rapide",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Button
        Button(
            onClick = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
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

        Spacer(modifier = Modifier.height(40.dp))
    }
}