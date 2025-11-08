package com.example.artisana.features.ui.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
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

data class CartItem(
    val id: String,
    val name: String,
    val price: String,
    val imageRes: Int,
    var quantity: Int = 1
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()

    val cartItems = remember {
        mutableStateListOf(
            CartItem("1", "BRASS PENDANT", "MAD 890", 0, 1),
            CartItem("2", "BRASS PENDANT", "MAD 760", 0, 1)
        )
    }

    val subtotal = remember(cartItems.toList()) {
        cartItems.sumOf { item ->
            val price = item.price.replace("MAD ", "").toIntOrNull() ?: 0
            price * item.quantity
        }
    }

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

                cartItems.forEach { item ->
                    CartItemCard(
                        item = item,
                        onQuantityChange = { newQuantity ->
                            val index = cartItems.indexOf(item)
                            if (newQuantity > 0) {
                                cartItems[index] = item.copy(quantity = newQuantity)
                            }
                        },
                        onRemove = {
                            cartItems.remove(item)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
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
                        "BUY NOW",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        // Product Image
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            // Replace with: Image(painter = painterResource(item.imageRes), ...)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Product Details
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = item.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.price,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Quantity Controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(
                    onClick = { onQuantityChange(item.quantity - 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text(
                        "−",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    text = item.quantity.toString(),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.widthIn(min = 20.dp),
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = { onQuantityChange(item.quantity + 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Text(
                        "+",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}