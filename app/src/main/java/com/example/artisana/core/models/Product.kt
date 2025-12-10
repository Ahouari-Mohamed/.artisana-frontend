package com.example.artisana.core.models

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val imageRes: List<String>,
    val stock: Int = 0,
    val isFavorite: Boolean = false,
    val isOnCart: Boolean = false,
    val quantity: Int = 0
)

data class ProductListState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)