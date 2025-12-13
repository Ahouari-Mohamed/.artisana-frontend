package com.example.artisana.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

// Type converter for Room to handle List<String>
class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}

@Entity(tableName = "products")
@TypeConverters(Converters::class)
data class Product(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("imageRes")
    val imageRes: List<String>,

    @SerializedName("stock")
    val stock: Int = 0,

    // Local-only fields (not from API)
    var isFavorite: Boolean = false,
    var isOnCart: Boolean = false,
    var quantity: Int = 0
)

data class ProductListState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)