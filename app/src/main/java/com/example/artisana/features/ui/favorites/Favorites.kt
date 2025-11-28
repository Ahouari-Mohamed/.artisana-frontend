package com.example.artisana.features.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.artisana.R
import com.example.artisana.core.composables.BottomNavigationBar
import com.example.artisana.core.composables.currentRoute
import com.example.artisana.core.navigation.Screen
import com.example.artisana.core.viewmodels.ProductViewModel
import com.example.artisana.core.viewmodels.ProductViewModelFactory
import com.example.artisana.features.composables.ResultCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory())
) {
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsState()

    val favoriteItems = remember(state.products) {
        viewModel.getFavoriteProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Favoris",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    Spacer(modifier = Modifier.size(48.dp))
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Cart.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_shopping_bag),
                            contentDescription = "Cart",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
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
        when {
            state.isLoading -> {
                // Loading state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            state.error != null -> {
                // Error state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = state.error ?: "Unknown error",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewModel.loadProducts() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(scrollState)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Favorite Products
                    if (favoriteItems.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Aucun produit trouvé",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        favoriteItems.forEach { product ->
                            ResultCard(
                                product = product,
                                page = "Favorites",
                                onClick = {
                                    navController.navigate(
                                        Screen.Details.createRoute(productId = product.id.toString())
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
