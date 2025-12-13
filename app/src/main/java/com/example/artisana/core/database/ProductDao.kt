package com.example.artisana.core.database

import androidx.room.*
import com.example.artisana.core.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // Get all products
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    // Get product by ID
    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): Product?

    // Get favorite products
    @Query("SELECT * FROM products WHERE isFavorite = 1")
    fun getFavoriteProducts(): Flow<List<Product>>

    // Get cart products
    @Query("SELECT * FROM products WHERE isOnCart = 1")
    fun getCartProducts(): Flow<List<Product>>

    // Add to favorite
    @Query("UPDATE products SET isFavorite = 1 WHERE id = :productId")
    suspend fun addToFavorites(productId: Int)

    // Remove from favorite
    @Query("UPDATE products SET isFavorite = 0 WHERE id = :productId")
    suspend fun removeFromFavorites(productId: Int)

    // check if product is in favorites
    @Query("SELECT EXISTS(SELECT isFavorite FROM products WHERE id = :productId AND isFavorite = 1)")
    fun isProductInFavorites(productId: Int): Flow<Boolean>

    // Add to cart
    @Query("UPDATE products SET isOnCart = 1 WHERE id = :productId")
    suspend fun addToCart(productId: Int)

    // Remove from cart
    @Query("UPDATE products SET isOnCart = 0 WHERE id = :productId")
    suspend fun removeFromCart(productId: Int)

    // check if product is in cart
    @Query("SELECT EXISTS(SELECT isOnCart FROM products WHERE id = :productId AND isOnCart = 1)")
    fun isProductInCart(productId: Int): Flow<Boolean>

    // Update quantity
    @Query("UPDATE products SET quantity = :quantity WHERE id = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

}