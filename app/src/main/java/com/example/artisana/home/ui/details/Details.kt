package com.example.artisana.home.ui.details

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.example.artisana.R
import com.example.artisana.core.models.Product
import com.example.artisana.home.composables.ProductCard
import com.example.artisana.core.navigation.Screen
import com.example.artisana.core.viewmodels.ProductViewModel
import com.google.accompanist.placeholder.shimmer
import kotlinx.coroutines.launch

data class ServiceInfo(
    val icon: Int,
    val text: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    productId: String,
) {
    val scrollState = rememberScrollState()
    val state by productViewModel.state.collectAsState()

    var selectedImageIndex by remember { mutableIntStateOf(0) }

    // Find the current product
    var product by remember { mutableStateOf<Product?>(null) }

    // Get recommended products
    var recommendedProducts by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(key1 = true) {
        product = productViewModel.getProductById(productId.toInt())
        recommendedProducts = productViewModel.getRecommendProducts(productId.toInt())
    }

    val services = listOf(
        ServiceInfo(R.drawable.ic_shipping, "Livraison forfaitaire gratuite"),
        ServiceInfo(R.drawable.ic_paiment_politique, "Politique de paiement à la livraison"),
        ServiceInfo(R.drawable.ic_refresh, "Politique de retour")
    )

    val isFavorite = remember { mutableStateOf<Boolean?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(product) {
        val currentProduct = product
        if (currentProduct != null) {
            productViewModel.isProductInFavorites(currentProduct.id).collect { status ->
                isFavorite.value = status
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Produit",
                        fontSize = 18.sp,
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
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                state.isLoading || product == null -> {
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

                else -> {
                    // Success state - Show product details
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        // Main Product Image
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(380.dp)
                                .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.1f))
                        ) {
                            SubcomposeAsyncImage(
                                model = product!!.imageRes[selectedImageIndex],
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                loading = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .placeholder(
                                                visible = true,
                                                shape = RectangleShape,
                                                color = MaterialTheme.colorScheme.background,
                                                highlight = PlaceholderHighlight.shimmer(
                                                    highlightColor = Color.White.copy(alpha = 0.7f),
                                                ),
                                            )
                                    )
                                },
                                error = {
                                    // Optional: Show an icon if the URL fails to load
                                    Image(
                                        imageVector = Icons.Filled.Build,
                                        contentDescription = "Error",
                                        modifier = Modifier.size(100.dp)
                                    )
                                }
                            )

                            // Favorite button - uses live data
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (isFavorite.value == true) {
                                            productViewModel.removeFromFavorites(product!!.id)
                                        } else {
                                            productViewModel.addToFavorites(product!!.id)
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    if (isFavorite.value == true) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite.value == true) MaterialTheme.colorScheme.primary else Color.White.copy(
                                        0.5f
                                    )
                                )
                            }
                        }

                        // Image Thumbnails
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterHorizontally
                            ),
                        ) {
                            items(4) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            MaterialTheme.colorScheme.inverseSurface.copy(
                                                alpha = 0.1f
                                            )
                                        )
                                        .border(
                                            width = if (selectedImageIndex == index) 2.dp else 0.dp,
                                            color = if (selectedImageIndex == index) MaterialTheme.colorScheme.primary else Color.Transparent,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { selectedImageIndex = index },
                                    contentAlignment = Alignment.Center
                                ) {
                                    SubcomposeAsyncImage(
                                        model = product!!.imageRes[index],
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop,
                                        loading = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .placeholder(
                                                        visible = true,
                                                        shape = RectangleShape,
                                                        color = MaterialTheme.colorScheme.background,
                                                        highlight = PlaceholderHighlight.shimmer(
                                                            highlightColor = Color.White.copy(alpha = 0.7f),
                                                        ),
                                                    )
                                            )
                                        },
                                        error = {
                                            // Optional: Show an icon if the URL fails to load
                                            Image(
                                                imageVector = Icons.Filled.Build,
                                                contentDescription = "Error",
                                                modifier = Modifier.size(100.dp)
                                            )
                                        }
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
                            ) {
                                Text(
                                    text = product!!.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                if (product!!.stock > 0) {
                                    Text(
                                        text = "En Stock",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    Text(
                                        text = "En rupture",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Price
                            Text(
                                text = product!!.price,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Description Section
                            Text(
                                text = "DESCRIPTION",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = product!!.description,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            OutlinedButton(
                                onClick = {
                                    productViewModel.addToCart(product!!.id)
                                    productViewModel.updateQuantity(product!!.id, 1)
                                    navController.navigate(Screen.Cart.route)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = androidx.compose.ui.graphics.SolidColor(
                                        MaterialTheme.colorScheme.primary
                                    )
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
                                    "AJOUTER AU PANIER",
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
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            services.forEach { service ->
                                ServiceItem(service)
                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            // Recommended Section
                            if (recommendedProducts.isNotEmpty()) {
                                Text(
                                    text = "RECOMMANDÉ POUR VOUS",
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                    letterSpacing = 2.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_separator),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }

                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }

                        // Recommended Products Grid - uses live data
                        if (recommendedProducts.isNotEmpty()) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height((recommendedProducts.size / 2 * 280).dp)
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(30.dp),
                                userScrollEnabled = false
                            ) {
                                items(recommendedProducts) { recommendedProduct ->
                                    ProductCard(
                                        product = recommendedProduct,
                                        onClick = {
                                            navController.navigate(
                                                Screen.Details.createRoute(productId = recommendedProduct.id.toString())
                                            )
                                        },
                                        productViewModel = productViewModel
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
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
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = service.text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}