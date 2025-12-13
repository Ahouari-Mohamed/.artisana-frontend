package com.example.artisana.features.ui.cart

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artisana.R
import com.example.artisana.core.navigation.Screen
import com.example.artisana.core.notifications.NotificationService
import com.example.artisana.core.viewmodels.ProductViewModel
import com.example.artisana.features.composables.ResultCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel
) {
    val scrollState = rememberScrollState()
    val state by productViewModel.state.collectAsState()
    val context = LocalContext.current
    val notificationService = remember { NotificationService(context) }

    val cartItems by remember { productViewModel.getCartProducts() }.collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Panier",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_return),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
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
                            onClick = { productViewModel.loadProducts() },
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
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(scrollState)
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        if (cartItems.isEmpty()) {
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
                            cartItems.forEach { product ->
                                ResultCard(
                                    product = product,
                                    page = "Cart",
                                    onClick = {
                                        navController.navigate(
                                            Screen.Details.createRoute(productId = product.id.toString())
                                        )
                                    },
                                    productViewModel = productViewModel
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }

                    val subtotal = remember(cartItems.toList()) {
                        cartItems.sumOf { item ->
                            val price = item.price.replace(" MAD", "").toIntOrNull() ?: 0
                            price * item.quantity
                        }
                    }

                    if (subtotal > 3000) {
                        notificationService.showCartLimitNotification()
                    }

                    // Bottom Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp)
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.padding(bottom = 16.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "SOUS-TOTAL",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "MAD $subtotal",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "*Les frais de livraison, les taxes et les codes de réduction sont calculés au moment de la facturation.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = {
                                Toast.makeText(context, "Merci pour votre achat!", Toast.LENGTH_SHORT).show()
                                productViewModel.clearCart()
                            },
                            enabled = cartItems.isNotEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContentColor = Color.Gray
                            ),
                            border = BorderStroke(
                                1.dp,
                                if (cartItems.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        ) {
                            Text(
                                "Acheter Maintenant",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
