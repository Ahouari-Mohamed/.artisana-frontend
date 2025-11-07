package com.example.artisana.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Product(
    val id: String,
    val name: String,
    val price: String,
    val imageRes: Int,
    var isFavorite: Boolean = false
)

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick) // Apply clickable to the whole column
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color(0xFFF5F5F5))
        ) {
            // Placeholder for product image
            // Replace with: Image(painter = painterResource(product.imageRes), ...)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 8.dp, bottom = 8.dp)
                    .size(36.dp)
            ) {
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier.padding(top = 8.dp, start = 0.dp, end = 0.dp, bottom = 0.dp) // Adjust padding to match text alignment
        ) {
            Text(
                text = product.name,
                fontSize = 14.sp,
                color = Color(0xFF2C2C2C),
                fontWeight = FontWeight.Normal
            )
            Text(
                text = product.price,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFB8956A)
            )
        }
    }
}