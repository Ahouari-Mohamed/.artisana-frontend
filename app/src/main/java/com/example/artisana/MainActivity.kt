package com.example.artisana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.artisana.core.database.ProductDatabase
import com.example.artisana.core.navigation.SetupNavGraph
import com.example.artisana.core.repositories.ProductRepository
import com.example.artisana.core.theme.ArtisanaTheme
import com.example.artisana.core.viewmodels.ProductViewModel
import com.example.artisana.core.viewmodels.ProductViewModelFactory
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
}