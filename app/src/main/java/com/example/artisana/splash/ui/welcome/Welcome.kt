package com.example.artisana.splash.ui.welcome

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artisana.core.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavHostController) {
    val brownColor = Color(0xFFB4916C)
    val lightBrownColor = Color(0xFFC2B89C)

    // Animated dots
    val infiniteTransition = rememberInfiniteTransition(label = "dots")

    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )

    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2"
    )

    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, delayMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3"
    )

    // Navigate after 3 seconds
    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate(Screen.Onboarding.route) {
            popUpTo(Screen.Welcome.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Decorative dots in background
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Top left dot
            drawCircle(
                color = brownColor.copy(alpha = 0.3f),
                radius = 8.dp.toPx(),
                center = Offset(50.dp.toPx(), 160.dp.toPx())
            )
            // Top right dot
            drawCircle(
                color = lightBrownColor.copy(alpha = 0.2f),
                radius = 6.dp.toPx(),
                center = Offset(size.width - 70.dp.toPx(), 200.dp.toPx())
            )
            // Bottom right dot
            drawCircle(
                color = lightBrownColor.copy(alpha = 0.25f),
                radius = 7.dp.toPx(),
                center = Offset(size.width - 60.dp.toPx(), size.height - 340.dp.toPx())
            )
        }

        Column(
            Modifier.fillMaxSize()
                .padding(vertical = 30.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Canvas(modifier = Modifier.size(16.dp)) {
                    drawCircle(color = brownColor)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Artisana",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Normal,
                    color = brownColor
                )
            }

            Spacer(Modifier.fillMaxHeight(.55f))

            // Loading dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = brownColor.copy(alpha = dot1Alpha))
                }
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = brownColor.copy(alpha = dot2Alpha))
                }
                Canvas(modifier = Modifier.size(10.dp)) {
                    drawCircle(color = brownColor.copy(alpha = dot3Alpha))
                }
            }
        }
    }
}