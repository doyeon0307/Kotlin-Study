package com.example.coco.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coco.db.entity.SelectedCoinPriceEntity

@Dao
interface SelectedCoinPriceDAO {

    // getAllData
    @Query("SELECT * FROM selected_coin_price_table")
    fun getAllData():List<SelectedCoinPriceEntity>

    // insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)

    // getOne
    @Query("SELECT * FROM selected_coin_price_table WHERE coinName = :coinName")
    fun getOneCoinData(coinName: String): List<SelectedCoinPriceEntity>

}