package com.example.artisana.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.artisana.core.models.ProductListState
import com.example.artisana.core.repositories.ProductRepository
import com.example.artisana.core.repositories.StaticProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository = StaticProductRepository.getInstance()
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListState(isLoading = true))
    val state: StateFlow<ProductListState> = _state.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val products = repository.getProducts()
                _state.update {
                    it.copy(
                        products = products,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Failed to load products: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }


    fun getBestSellers(limit: Int = 2) = _state.value.products.take(limit)

    fun getRecommendProducts(productId: Int) = _state.value.products.filter { it.id != productId }.take(4)

    fun getFavoriteProducts() = _state.value.products.filter { it.isFavorite }

    fun toggleFavorite(productId: Int) {
        viewModelScope.launch {
            try {
                val updatedProduct = repository.toggleFavorite(productId)
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
                    it.copy(error = "Failed to update favorite: ${e.message}")
                }
            }
        }
    }

    fun getCartProducts() = _state.value.products.filter { it.isOnCart }

    fun toggleCart(productId: Int) {
        viewModelScope.launch {
            try {
                val updatedProduct = repository.toggleCart(productId)
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
                    it.copy(error = "Failed to update favorite: ${e.message}")
                }
            }
        }
    }

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

// Factory for creating ViewModel with singleton repository
class ProductViewModelFactory(
    private val repository: ProductRepository = StaticProductRepository.getInstance()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}