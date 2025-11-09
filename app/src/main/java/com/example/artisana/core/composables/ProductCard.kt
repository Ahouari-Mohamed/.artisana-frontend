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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.1f))
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
                    tint = MaterialTheme.colorScheme.onBackground.copy(0.5f),
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
                    imageVector = if (product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (product.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(0.5f)
                )
            }
        }

        Column(
            modifier = Modifier.padding(top = 8.dp, start = 0.dp, end = 0.dp, bottom = 0.dp)
        ) {
            Text(
                text = product.name,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = product.price,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}