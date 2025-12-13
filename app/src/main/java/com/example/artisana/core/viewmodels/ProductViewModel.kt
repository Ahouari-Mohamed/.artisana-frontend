package com.example.artisana.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artisana.core.models.Product
import com.example.artisana.core.models.ProductListState
import com.example.artisana.core.repositories.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState(isLoading = true))
    val state: StateFlow<ProductListState> = _state.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> = _searchResults.asStateFlow()
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()


    init {
        loadProducts()
    }

    private fun updateSearchResultsWithAllProducts() {
        // update the search results if the user is NOT typing
        if (!_isSearching.value) {
            _searchResults.value = _state.value.products
        }
    }

    // Load products from API
    fun loadProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val response = repository.getProductsFromApi()
                if (response.isSuccessful) {
                    response.body()?.let { products ->
                        _state.update {
                            it.copy(
                                products = products,
                                isLoading = false
                            )
                        }
                    }
                    updateSearchResultsWithAllProducts()
                } else {
                    // Load from local database if API fails
                    repository.getAllProductsFromDb().collect { localProducts ->
                        _state.update {
                            it.copy(
                                products = localProducts,
                                isLoading = false,
                                error = "Loaded from cache. API Error: ${response.code()}"
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                // Load from local database on network error
                repository.getAllProductsFromDb().collect { localProducts ->
                    _state.update {
                        it.copy(
                            products = localProducts,
                            isLoading = false,
                            error = "Network error: ${e.message}. Showing cached data."
                        )
                    }
                }
            }
        }
    }

    // Get product by ID from API
    suspend fun getProductById(id: Int): Product? {
        try {
            val response = repository.getProductById(id)
            if (response.isSuccessful) {
                return response.body()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    // Search products from API
    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _searchResults.value = _state.value.products
            _isSearching.value = false
            return
        }

        viewModelScope.launch {
            _isSearching.value = true
            try {
                val response = repository.searchProducts(query)
                if (response.isSuccessful) {
                    response.body()?.let { results ->
                        _searchResults.value = results
                    }
                } else {
                    _searchResults.value = emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _searchResults.value = emptyList()
            } finally {
                _isSearching.value = false
            }
        }
    }

    // Get best sellers from repository
    suspend fun getBestSellers(limit: Int = 2): List<Product> {
        try {
            val response = repository.getBestSellers()
            if (response.isSuccessful) {
                return response.body()?.take(limit) ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    // Get recommended products from repository
    suspend fun getRecommendProducts(productId: Int): List<Product> {
        try {
            val response = repository.getRecommendedProducts()
            if (response.isSuccessful) {
                return response.body()?.filter { it.id != productId } ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    // Get favorite products from repository
    fun getFavoriteProducts(): Flow<List<Product>> {
        return repository.getFavoriteProducts()
    }

    // Add to favorite
    fun addToFavorites(productId: Int) {
        viewModelScope.launch {
            try {
                val updatedProduct = repository.addProductToFavorites(productId)
                if (updatedProduct != null) {
                    _state.update { currentState ->
                        val updatedProducts = currentState.products.map { product ->
                            if (product.id == productId) updatedProduct else product
                        }
                        currentState.copy(products = updatedProducts)
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to update favorites: ${e.message}")
                }
            }
        }
    }

    // Remove from favorite
    fun removeFromFavorites(productId: Int) {
        viewModelScope.launch {
            try {
                val updatedProduct = repository.removeProductFromFavorites(productId)
                if (updatedProduct != null) {
                    _state.update { currentState ->
                        val updatedProducts = currentState.products.map { product ->
                            if (product.id == productId) updatedProduct else product
                        }
                        currentState.copy(products = updatedProducts)
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to update favorites: ${e.message}")
                }
            }
        }
    }

    // Check if product is in favorites
    fun isProductInFavorites(productId: Int): Flow<Boolean> {
        return repository.isProductInFavorites(productId)
    }

    // Get cart products from repository
    fun getCartProducts(): Flow<List<Product>> {
        return repository.getCartProducts()
    }

    // Add to cart
    fun addToCart(productId: Int) {
        viewModelScope.launch {
            try {
                val updatedProduct = repository.addProductToCart(productId)
                if (updatedProduct != null) {
                    _state.update { currentState ->
                        val updatedProducts = currentState.products.map { product ->
                            if (product.id == productId) updatedProduct else product
                        }
                        currentState.copy(products = updatedProducts)
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to update cart: ${e.message}")
                }
            }
        }
    }

    // Remove from cart
    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            try {
                val updatedProduct = repository.removeProductFromCart(productId)
                if (updatedProduct != null) {
                    _state.update { currentState ->
                        val updatedProducts = currentState.products.map { product ->
                            if (product.id == productId) updatedProduct else product
                        }
                        currentState.copy(products = updatedProducts)
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to update cart: ${e.message}")
                }
            }
        }
    }

    // Check if product is in cart
    fun isProductInCart(productId: Int): Flow<Boolean> {
        return repository.isProductInCart(productId)
    }

    // Update product quantity
    fun updateQuantity(productId: Int, newQuantity: Int) {
        viewModelScope.launch {
            try {
                val updatedProduct = repository.updateQuantity(productId, newQuantity)
                if (updatedProduct != null) {
                    _state.update { currentState ->
                        val updatedProducts = currentState.products.map { product ->
                            if (product.id == productId) updatedProduct else product
                        }
                        currentState.copy(products = updatedProducts)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.update {
                    it.copy(error = "Failed to update quantity: ${e.message}")
                }
            }
        }
    }
}