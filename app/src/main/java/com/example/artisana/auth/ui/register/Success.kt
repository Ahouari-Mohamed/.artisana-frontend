package com.example.artisana.auth.ui.register

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.artisana.core.navigation.Screen

@Composable
fun AccountSuccessScreen(navController: NavHostController) {
    val textColor = Color(0xFFB08D5B)
    val darkBrownColor = Color(0xFFB08D5B)
    val iconColor = Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Success Icon
        Canvas(modifier = Modifier.size(150.dp)) {
            val iconSize = size.width
            val strokeWidth = 3.dp.toPx()

            drawCircle(
                color = iconColor,
                radius = iconSize * 0.12f,
                center = Offset(iconSize * 0.3f, iconSize * 0.35f),
                style = Stroke(width = strokeWidth)
            )

            drawArc(
                color = iconColor,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(iconSize * 0.15f, iconSize * 0.45f),
                size = androidx.compose.ui.geometry.Size(iconSize * 0.3f, iconSize * 0.25f),
                style = Stroke(width = strokeWidth)
            )

            val rectLeft = iconSize * 0.52f
            val rectTop = iconSize * 0.25f
            val rectWidth = iconSize * 0.35f
            val rectHeight = iconSize * 0.48f
            val cornerRadius = 15f

            drawRoundRect(
                color = iconColor,
                topLeft = Offset(rectLeft, rectTop),
                size = androidx.compose.ui.geometry.Size(rectWidth, rectHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius),
                style = Stroke(width = strokeWidth)
            )

            val checkStartX = iconSize * 0.58f
            val checkStartY = iconSize * 0.42f
            val checkMidX = iconSize * 0.65f
            val checkMidY = iconSize * 0.48f
            val checkEndX = iconSize * 0.80f
            val checkEndY = iconSize * 0.32f

            drawLine(
                color = iconColor,
                start = Offset(checkStartX, checkStartY),
                end = Offset(checkMidX, checkMidY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = iconColor,
                start = Offset(checkMidX, checkMidY),
                end = Offset(checkEndX, checkEndY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )

            val lineY1 = iconSize * 0.56f
            val lineY2 = iconSize * 0.63f
            val lineStartX = iconSize * 0.58f
            val lineEndX = iconSize * 0.82f

            drawLine(
                color = iconColor,
                start = Offset(lineStartX, lineY1),
                end = Offset(lineEndX, lineY1),
                strokeWidth = strokeWidth * 0.8f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = iconColor,
                start = Offset(lineStartX, lineY2),
                end = Offset(lineEndX, lineY2),
                strokeWidth = strokeWidth * 0.8f,
                cap = StrokeCap.Round
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = "Compte créé avec succès",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Subtitle
        Text(
            text = "Votre compte est prêt, commençons notre aventure.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.weight(1.5f))

        // Continue button
        Button(
            onClick = {
                navController.navigate(Screen.Login.route) {
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = darkBrownColor)
        ) {
            Text(
                text = "Continuer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAccountSuccessScreen() {
    val navController = rememberNavController()
    AccountSuccessScreen(navController = navController)
}