package com.example.artisana.core.repositories

import com.example.artisana.core.database.ProductDao
import com.example.artisana.core.models.Product
import com.example.artisana.core.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.Response

class ProductRepository(private val productDao: ProductDao) {

    // Get products from API
    suspend fun getProductsFromApi(): Response<List<Product>> {

        val response = RetrofitInstance.productApiService.getAllProducts()

        if (response.isSuccessful) {
            val remoteProducts = response.body()
            val localProductsList: List<Product> = productDao.getAllProducts().first()

            remoteProducts?.forEach { remoteProduct ->
                val localProduct = localProductsList.find { it.id == remoteProduct.id }

                if (localProduct != null) {
                    remoteProduct.isFavorite = localProduct.isFavorite
                    remoteProduct.isOnCart = localProduct.isOnCart
                    remoteProduct.quantity = localProduct.quantity
                }
            }
        }

        return response
    }

    // Get all products from local database
    fun getAllProductsFromDb(): Flow<List<Product>> = productDao.getAllProducts()

    // Search products from API
    suspend fun searchProducts(query: String): Response<List<Product>> {
        return RetrofitInstance.productApiService.searchProducts(query)
    }

    // Get bestsellers from API
    suspend fun getBestSellers(): Response<List<Product>> {
        return RetrofitInstance.productApiService.getBestSellers()
    }

    // Get recommended products from API
    suspend fun getRecommendedProducts(): Response<List<Product>> {
        return RetrofitInstance.productApiService.getRecommendedProducts()
    }

    // Get product by ID from API
    suspend fun getProductById(id: Int): Response<Product> {
        return RetrofitInstance.productApiService.getProductById(id)
    }

    // Get favorite products from local database
    fun getFavoriteProducts(): Flow<List<Product>> = productDao.getFavoriteProducts()

    // Get cart products from local database
    fun getCartProducts(): Flow<List<Product>> = productDao.getCartProducts()

    // Add to favorite(local only)
    suspend fun addProductToFavorites(productId: Int): Product? {
        val product = RetrofitInstance.productApiService.getProductById(productId)

        // check if product exists locally
        val localProduct = productDao.getProductById(productId)
        if (localProduct != null) {
            productDao.addToFavorites(productId)
        } else {
            product.body()?.let {
                it.isFavorite = true
                productDao.insert(it)
            }
        }

        return null
    }

    // Remove from favorite(local only)
    suspend fun removeProductFromFavorites(productId: Int): Product? {
        // check if product exists locally
        val localProduct = productDao.getProductById(productId)
        if (localProduct != null) {
            productDao.removeFromFavorites(productId)
        }

        return null
    }

    // check if product is in favorites
    fun isProductInFavorites(productId: Int): Flow<Boolean> {
        return productDao.isProductInFavorites(productId)
    }

    // Add to cart(local only)
    suspend fun addProductToCart(productId: Int): Product? {
        val product = RetrofitInstance.productApiService.getProductById(productId)

        // check if product exists locally
        val localProduct = productDao.getProductById(productId)
        if (localProduct != null) {
            productDao.addToCart(productId)
        } else {
            product.body()?.let {
                it.isOnCart = true
                productDao.insert(it)
            }
        }

        return null
    }

    // Remove from cart(local only)
    suspend fun removeProductFromCart(productId: Int): Product? {
        // check if product exists locally
        val localProduct = productDao.getProductById(productId)
        if (localProduct != null) {
            productDao.removeFromCart(productId)
        }

        return null
    }

    suspend fun clearCart() {
        productDao.clearCart()
    }

    // check if product is in cart
    fun isProductInCart(productId: Int): Flow<Boolean> {
        return productDao.isProductInCart(productId)
    }

    // Update quantity (local only)
    suspend fun updateQuantity(productId: Int, newQuantity: Int): Product? {
        productDao.updateQuantity(productId, newQuantity)
        return productDao.getProductById(productId)
    }

}