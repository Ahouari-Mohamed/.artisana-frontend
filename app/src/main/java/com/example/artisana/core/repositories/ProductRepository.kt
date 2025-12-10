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
            imageRes = listOf(
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382700/img_1_1_odndzn.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382883/img_1_2_xhcah2.jpg",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382697/img_1_3_eowjge.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382694/img_1_4_cr5saq.png",
            ),
            stock = 0
        ),
        Product(
            id = 2,
            name = "Suspension en Laiton - Étoile",
            description = "Suspension cylindrique en laiton perforé avec motifs d'étoiles.",
            price = "480 MAD",
            imageRes = listOf(
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382882/img_2_1_wftora.jpg",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382696/img_2_2_qgwj6n.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382696/img_2_3_qkqc5s.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382696/img_2_4_wh2z7f.png",
            ),
            stock = 9
        ),
        Product(
            id = 3,
            name = "Pouf en Cuir Brodé - POFA",
            description = "Pouf marocain artisanal en cuir, broderie rouge et motifs circulaires.",
            price = "260 MAD",
            imageRes = listOf(
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382881/img_3_1_sskfvv.jpg",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382690/img_3_2_lb5pth.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382687/img_3_3_rv9y7z.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382691/img_3_4_hzlkol.png",
            ),
            stock = 5
        ),
        Product(
            id = 4,
            name = "Suspension en Laiton - Globe Perforé",
            description = "Grande lanterne en laiton avec dôme et perforations en forme d'amande.",
            price = "280 MAD",
            imageRes = listOf(
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382885/img_4_1_jkifai.jpg",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382699/img_4_2_lomute.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382700/img_4_3_nvssjw.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382683/img_4_4_tfcb6q.png",
            ),
            stock = 0
        ),
        Product(
            id = 5,
            name = "Suspension en Laiton - Ambre",
            description = "Suspension en laiton avec un verre de couleur ambre pour un éclairage doux.",
            price = "790 MAD",
            imageRes = listOf(
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382882/img_5_1_octpwy.jpg",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382685/img_5_2_r5doqs.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382684/img_5_3_gquwsl.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382688/img_5_4_s95ytf.png"
            ),
            stock = 1
        ),
        Product(
            id = 6,
            name = "Suspension en Laiton - Dôme",
            description = "Suspension semi-sphérique en laiton finement ciselé, idéale pour les plafonds bas.",
            price = "890 MAD",
            imageRes = listOf(
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382881/img_6_1_qymgns.jpg",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382681/img_6_2_sa1jxr.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382683/img_6_3_drwmfg.png",
                "https://res.cloudinary.com/dmbitesey/image/upload/v1765382679/img_6_4_vrkiu7.png"
            ),
            stock = 2
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