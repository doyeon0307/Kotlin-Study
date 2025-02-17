package com.example.coco.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("interest_coin_table")
data class InterestCoinEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val coin_name: String,
    val opening_price: String,
    val closing_price: String,
    val min_price: String,
    val max_price: String,
    val units_traded: String,
    val acc_trade_value: String,
    val prev_closing_price: String,
    val units_traded_24H: String,
    val acc_trade_value_24H: String,
    val fluctate_24H: String,
    val fluctate_rate_24H: String,
    var selected: Boolean

)