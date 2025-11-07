package com.example.artisana.home.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import com.example.artisana.core.composables.Product
import com.example.artisana.core.composables.ProductCard
import com.example.artisana.core.navigation.Screen

data class ServiceInfo(
    val icon: Int,
    val text: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    productId: String
) {
    val scrollState = rememberScrollState()
    var selectedImageIndex by remember { mutableStateOf(0) }
    var isFavorite by remember { mutableStateOf(false) }

    // Sample product data
    val product = remember {
        Product(
            id = productId,
            name = "BRASS PENDANT",
            price = "MAD 890",
            imageRes = 0,
        )
    }

    val recommendedProducts = remember {
        listOf(
            Product("2", "Brass Pendant", "MAD 760", 0),
            Product("3", "Brass Pendant", "MAD 480", 0),
            Product("4", "Brass Pendant", "MAD 280", 0),
            Product("5", "Brass Pendant", "MAD 790", 0)
        )
    }

    val services = listOf(
        ServiceInfo(R.drawable.ic_shipping, "Livraison forfaitaire gratuite"),
        ServiceInfo(R.drawable.ic_paiment_politique, "Politique de paiement à la livraison"),
        ServiceInfo(R.drawable.ic_refresh, "Politique de retour")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Produit", fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_return),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Cart.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_shopping_bag),
                            contentDescription = "Cart",
                            tint = Color(0xFF8B7355)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(Color.White)
        ) {
            // Main Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .background(Color(0xFFF5F5F5))
            ) {
                // Placeholder for main product image
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Home,
                        contentDescription = null,
                        tint = Color(0xFFB8956A),
                        modifier = Modifier.size(80.dp)
                    )
                }

                // Favorite button
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.White
                    )
                }
            }

            // Image Thumbnails
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(4) { index ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5F5F5))
                            .border(
                                width = if (selectedImageIndex == index) 2.dp else 0.dp,
                                color = if (selectedImageIndex == index) Color(0xFFB8956A) else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { selectedImageIndex = index },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            // Product Info Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Product Name and Stock
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2C2C2C),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "(in Stock)",
                        fontSize = 16.sp,
                        color = Color(0xFFB8956A),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Price
                Text(
                    text = product.price,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFB8956A)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Description Section
                Text(
                    text = "DESCRIPTION",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2C2C2C),
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Découvrez l'élégance intemporelle de cette suspension en laiton, un luminaire d'inspiration marocaine qui apporte une touche de luxe et de sophistication à tout intérieur. Sa finition en laiton crée une ambiance chaleureuse et raffinée, évoquant le chic parisien classique. Idéale pour éclairer une table à manger ou un salon, elle diffuse une lumière douce et accueillante, devenant ainsi la pièce maîtresse rayonnante de votre décoration.",
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = Color(0xFF757575)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Add to Basket Button
                OutlinedButton(
                    onClick = { /* Add to basket */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFB8956A)
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFB8956A))
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "ADD TO BASKET",
                        fontSize = 14.sp,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Services Section
                Text(
                    text = "Entretien",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2C2C2C)
                )

                Spacer(modifier = Modifier.height(16.dp))

                services.forEach { service ->
                    ServiceItem(service)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Recommended Section
                Text(
                    text = "RECOMMANDÉ POUR VOUS",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp,
                    color = Color(0xFF2C2C2C)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_separator),
                        contentDescription = null,
                        tint = Color(0xFFB8956A),
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Recommended Products Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                userScrollEnabled = false
            ) {
                items(recommendedProducts) { product ->
                    ProductCard(
                        product = product,
                        modifier = Modifier,
                        onFavoriteClick = {

                        },
                        onClick = {
                            navController.navigate(Screen.Details.createRoute(productId = product.id))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ServiceItem(service: ServiceInfo) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(service.icon),
            contentDescription = null,
            tint = Color(0xFF757575),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = service.text,
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
    }
}
