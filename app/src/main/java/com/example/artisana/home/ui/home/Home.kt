package com.example.artisana.home.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artisana.R
import com.example.artisana.core.composables.BottomNavigationBar
import com.example.artisana.core.composables.Product
import com.example.artisana.core.composables.ProductCard
import com.example.artisana.core.composables.currentRoute
import com.example.artisana.core.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val products = remember {
        mutableStateListOf(
            Product("1", "Brass Pendant", "MAD 760", 0),
            Product("2", "Brass Pendant", "MAD 480", 0)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Icon(
                        painterResource(R.drawable.ic_artisana),
                        contentDescription = "Logo",
                        tint = MaterialTheme.colorScheme.primary,
                    )
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Hero Section with Artisan Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(250.dp)
            ) {

                Image(
                    painter = painterResource(R.drawable.img_artisan_hero),
                    contentDescription = "Artisan Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                )


                // Badges overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    BadgeItem("100% Fait main", R.drawable.ic_handcraft)
                    BadgeItem("100% Marocain", R.drawable.ic_moroccain)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Best Sellers Section
            Text(
                text = "NOS MEILLEURES VENTES",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_separator),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Products Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                products.forEach { product ->
                    ProductCard(
                        product = product,
                        modifier = Modifier.weight(1f),
                        onFavoriteClick = {
                            val index = products.indexOf(product)
                            products[index] = product.copy(isFavorite = !product.isFavorite)
                        },
                        onClick = {
                            navController.navigate(Screen.Details.createRoute(productId = product.id))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // View All Products Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Voir tous nos Produits",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = { navController.navigate(Screen.Search.route) }) {
                    Text(
                        "Explorer",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Gift Packaging Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "EMBALLAGES CADEAUX",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_separator),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_gift),
                    contentDescription = "Gift",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(68.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Besoin d'un emballage cadeau? Contactez-nous par chat, 24 h/24 et 7 j/7 ! Pour les commandes urgentes, veuillez nous appeler.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* Checkout action */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        "DISCUTEZ AVEC NOUS",
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun BadgeItem(text: String, iconId: Int) {
    Row(
        modifier = Modifier
            .background(Color.Transparent, RoundedCornerShape(4.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(25.dp)
            )
        }
        Text(
            text = text,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}