package com.example.artisana

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.artisana.core.database.ProductDatabase
import com.example.artisana.core.navigation.SetupNavGraph
import com.example.artisana.core.repositories.ProductRepository
import com.example.artisana.core.theme.ArtisanaTheme
import com.example.artisana.core.viewmodels.ProductViewModel
import com.example.artisana.core.viewmodels.ProductViewModelFactory

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        askNotificationPermission()

        // 1. Initialize the Room Database
        val db = Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java,
            "products_db" // Database name
        ).build()

        // 2. Get the DAO
        val productDao = db.productDao()

        // 3. Pass the DAO to the Repository
        val productRepository = ProductRepository(productDao)
        val viewModelFactory = ProductViewModelFactory(productRepository)

        setContent {
            val productViewModel: ProductViewModel = viewModel(factory = viewModelFactory)
            val systemTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemTheme) }

            ArtisanaTheme(
                darkTheme = isDarkTheme,
                toggleDarkTheme = {
                    isDarkTheme = it
                }
            ){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                        productViewModel = productViewModel
                    )
                }
            }
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
