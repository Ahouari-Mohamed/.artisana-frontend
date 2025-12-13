package com.example.artisana.features.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.artisana.core.models.Product
import com.example.artisana.core.viewmodels.ProductViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun ResultCard(
    product: Product,
    page: String,
    onClick: () -> Unit,
    productViewModel: ProductViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = product.imageRes[0],
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

        Spacer(modifier = Modifier.width(16.dp))

        // Product Details
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(0.dp, 3.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = product.price,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary
                    )

                }
                if (page == "Favorites") {
                    IconButton(
                        onClick = {
                            productViewModel.removeFromFavorites(product.id)
                        },
                        modifier = Modifier
                            .size(24.dp)
                            .padding(top = 10.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                if (page == "Cart") {
                    IconButton(
                        onClick = {
                            productViewModel.removeFromCart(product.id)
                        },
                        modifier = Modifier
                            .size(24.dp)
                            .padding(top = 10.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (page == "Cart") {
                // Quantity Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (product.quantity > 1)
                                productViewModel.updateQuantity(product.id, product.quantity - 1)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            "−",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        text = product.quantity.toString(),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.widthIn(min = 20.dp),
                        textAlign = TextAlign.Center
                    )

                    IconButton(
                        onClick = {
                            if (product.quantity < 10)
                                productViewModel.updateQuantity(product.id, product.quantity + 1)
                        },
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
}