package com.example.artisana.features.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.artisana.R
import com.example.artisana.core.viewmodels.AuthViewModel
import com.example.artisana.core.composables.BottomNavigationBar
import com.example.artisana.core.composables.currentRoute
import com.example.artisana.core.navigation.Screen
import com.example.artisana.core.theme.LocalThemeState
import com.example.artisana.features.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val uiState by profileViewModel.uiState.collectAsState()
    val isLoggedIn by authViewModel.isUserLoggedIn.collectAsState()

    val themeState = LocalThemeState.current
    val isDarkMode = themeState.isDark
    val toggleDarkMode = themeState.toggleDarkTheme

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(Screen.Login.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
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
                .background(MaterialTheme.colorScheme.background)
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
                    tint = MaterialTheme.colorScheme.onBackground,
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
                text = uiState.user?.displayName ?: "",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Email
            Text(
                text = uiState.user?.email ?: "",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
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
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Mode sombre",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { toggleDarkMode(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.surface,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                        uncheckedTrackColor = MaterialTheme.colorScheme.outline
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
                onClick = { authViewModel.signOut() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.primary
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
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (trailingText != null) {
            Text(
                text = trailingText,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}