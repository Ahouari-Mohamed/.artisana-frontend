package com.example.artisana.core.network

import com.example.artisana.core.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {

    // Get all products
    @GET("api/products")
    suspend fun getAllProducts(): Response<List<Product>>

    // Get product by ID
    @GET("api/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<Product>

    // Search products
    @GET("api/products/search")
    suspend fun searchProducts(@Query("query") query: String): Response<List<Product>>

    // Get bestsellers
    @GET("api/products/bestsellers")
    suspend fun getBestSellers(): Response<List<Product>>

    // Get recommended products
    @GET("api/products/recommended")
    suspend fun getRecommendedProducts(): Response<List<Product>>

    // Filter products by IDs
    @GET("api/products/filter")
    suspend fun filterProductsByIds(@Query("ids") ids: String): Response<List<Product>>

}