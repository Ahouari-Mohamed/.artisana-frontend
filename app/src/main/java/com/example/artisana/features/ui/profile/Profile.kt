package com.example.artisana.features.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artisana.R
import com.example.artisana.core.composables.BottomNavigationBar
import com.example.artisana.core.composables.currentRoute
import com.example.artisana.core.navigation.Screen
import com.example.artisana.core.theme.LocalThemeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()

    val themeState = LocalThemeState.current
    val isDarkMode = themeState.isDark // Read the current state
    val toggleDarkMode = themeState.toggleDarkTheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF2C2C2C),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = navController.currentRoute()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Profile Picture with Edit Icon
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                // Profile Picture
                Image(
                    painter = painterResource(R.drawable.img_user_image),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                )

                // Edit Icon
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "Edit",
                    tint = Color(0xFF2C2C2C),
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-16).dp, y = (-16).dp)
                        .clickable { /* Edit profile picture */ },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User Name
            Text(
                text = "Ahouari Mohamed",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C2C2C)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Email
            Text(
                text = "youremail@domain.com",
                fontSize = 14.sp,
                color = Color(0xFF999999)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Dark Mode Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_dark_mode),
                        contentDescription = "Dark Mode",
                        tint = Color(0xFF666666),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Mode sombre",
                        fontSize = 16.sp,
                        color = Color(0xFF2C2C2C)
                    )
                }
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { toggleDarkMode(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFFB8956A),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFFE0E0E0)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile Options
            ProfileMenuItem(
                R.drawable.ic_infos,
                text = "Modifier informations du profil",
                onClick = { /* Navigate to edit profile */ }
            )

            ProfileMenuItem(
                R.drawable.ic_language,
                text = "Langue",
                trailingText = "Français",
                onClick = { /* Navigate to language settings */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Support Section
            ProfileMenuItem(
                R.drawable.ic_support,
                text = "Aide & Support",
                onClick = { /* Navigate to support */ }
            )

            ProfileMenuItem(
                R.drawable.ic_contact,
                text = "Contactez-nous",
                onClick = { /* Navigate to contact */ }
            )

            ProfileMenuItem(
                R.drawable.ic_confidel,
                text = "Politique de confidentialité",
                onClick = { /* Navigate to privacy policy */ }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Logout Button
            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        // Pop up to the root of the entire navigation graph.
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFB8956A)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color(0xFFB8956A)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Déconnecter",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileMenuItem(
    id: Int,
    text: String,
    trailingText: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(id),
                contentDescription = text,
                tint = Color(0xFF666666),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color(0xFF2C2C2C)
            )
        }

        if (trailingText != null) {
            Text(
                text = trailingText,
                fontSize = 14.sp,
                color = Color(0xFFB8956A)
            )
        }
    }
}