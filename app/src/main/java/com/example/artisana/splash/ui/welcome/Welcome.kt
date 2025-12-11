package com.example.artisana.splash.ui.welcome

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.artisana.R
import com.example.artisana.core.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavHostController, isLoggedIn: Boolean
) {
    val brownColor = Color(0xFFB4916C)

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
        delay(3000)
        if (isLoggedIn) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Welcome.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Onboarding.route) {
                popUpTo(Screen.Welcome.route) { inclusive = true }
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.img_welcome_bg), // Replace with your image name
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 30.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Icon(
                painterResource(R.drawable.ic_artisana),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(240.dp)
                    .height(100.dp),
                tint = MaterialTheme.colorScheme.primary,
            )


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