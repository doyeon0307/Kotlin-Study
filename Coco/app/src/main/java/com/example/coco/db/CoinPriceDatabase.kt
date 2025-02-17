package com.example.coco.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.coco.db.dao.InterestCoinDAO
import com.example.coco.db.dao.SelectedCoinPriceDAO
import com.example.coco.db.entity.DateConverter
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.db.entity.SelectedCoinPriceEntity

@Database(entities = [InterestCoinEntity::class, SelectedCoinPriceEntity::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class CoinPriceDatabase : RoomDatabase() {

    abstract fun interestCoinDAO(): InterestCoinDAO
    abstract fun selectedCoinDAO(): SelectedCoinPriceDAO

    companion object {

        @Volatile
        private var INSTANCE: CoinPriceDatabase? = null

        fun getDatabase(context: Context): CoinPriceDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinPriceDatabase::class.java,
                    "coin_database"
                ).fallbackToDestructiveMigrationFrom()
                    .allowMainThreadQueries() // 개발 중에만 사용
                    .build()
                INSTANCE = instance
                instance
            }

        }

    }

}