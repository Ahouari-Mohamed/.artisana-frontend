package com.example.artisana.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.artisana.core.models.Converters
import com.example.artisana.core.models.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}