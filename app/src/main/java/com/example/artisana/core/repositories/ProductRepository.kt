package com.example.artisana.core.repositories

import com.example.artisana.R
import com.example.artisana.core.models.Product
import kotlinx.coroutines.delay

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun toggleFavorite(productId: Int): Product?
    suspend fun toggleCart(productId: Int): Product?
    suspend fun updateQuantity(productId: Int, newQuantity: Int): Product?
}

class StaticProductRepository private constructor() : ProductRepository {

    // Singleton instance
    companion object {
        @Volatile
        private var instance: StaticProductRepository? = null

        fun getInstance(): StaticProductRepository {
            return instance ?: synchronized(this) {
                instance ?: StaticProductRepository().also { instance = it }
            }
        }
    }

    private val products = mutableListOf(
        Product(
            id = 1,
            name = "Suspension en Laiton - Lanternin",
            description = "Lanterne en laiton ciselé, style marocain traditionnel avec motifs géométriques.",
            price = "760 MAD",
            imageRes = R.drawable.img_1,
        ),
        Product(
            id = 2,
            name = "Suspension en Laiton - Étoile",
            description = "Suspension cylindrique en laiton perforé avec motifs d'étoiles.",
            price = "480 MAD",
            imageRes = R.drawable.img_2,
        ),
        Product(
            id = 3,
            name = "Pouf en Cuir Brodé - POFA",
            description = "Pouf marocain artisanal en cuir, broderie rouge et motifs circulaires.",
            price = "260 MAD",
            imageRes = R.drawable.img_3,
        ),
        Product(
            id = 4,
            name = "Suspension en Laiton - Globe Perforé",
            description = "Grande lanterne en laiton avec dôme et perforations en forme d'amande.",
            price = "280 MAD",
            imageRes = R.drawable.img_4,
        ),
        Product(
            id = 5,
            name = "Suspension en Laiton - Ambre",
            description = "Suspension en laiton avec un verre de couleur ambre pour un éclairage doux.",
            price = "790 MAD",
            imageRes = R.drawable.img_5,
        ),
        Product(
            id = 6,
            name = "Suspension en Laiton - Dôme",
            description = "Suspension semi-sphérique en laiton finement ciselé, idéale pour les plafonds bas.",
            price = "890 MAD",
            imageRes = R.drawable.img_6,
        )
    )

    override suspend fun getProducts(): List<Product> {
        delay(500) // Simulate network delay

        return products.toList()
    }

    override suspend fun toggleFavorite(productId: Int): Product? {
        delay(100) // Simulate network delay

        val index = products.indexOfFirst { it.id == productId }
        if (index != -1) {
            val product = products[index]
            val updatedProduct = product.copy(isFavorite = !product.isFavorite)
            products[index] = updatedProduct
            return updatedProduct
        }
        return null
    }

    override suspend fun toggleCart(productId: Int): Product? {
        delay(100) // Simulate network delay

        val index = products.indexOfFirst { it.id == productId }
        if (index != -1) {
            val product = products[index]
            val updatedProduct = product.copy(isOnCart = !product.isOnCart)
            products[index] = updatedProduct
            return updatedProduct
        }
        return null
    }

    override suspend fun updateQuantity(productId: Int, newQuantity: Int): Product? {
        delay(100) // Simulate network delay

        val index = products.indexOfFirst { it.id == productId }
        if (index != -1) {
            val product = products[index]
            val updatedProduct = product.copy(quantity = newQuantity)
            products[index] = updatedProduct
            return updatedProduct
        }
        return null
    }
}