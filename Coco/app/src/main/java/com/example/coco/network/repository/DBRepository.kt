package com.example.coco.network.repository

import com.example.coco.App
import com.example.coco.db.CoinPriceDatabase
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.db.entity.SelectedCoinPriceEntity

class DBRepository {

    val context = App.context()
    private val db = CoinPriceDatabase.getDatabase(context)

    // InterestCoin

    fun getAllInterestCoinData() = db.interestCoinDAO().getAllData()

    fun insertInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().insert(interestCoinEntity)

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().update(interestCoinEntity)

    fun getAllInterestSelectedCoinData() = db.interestCoinDAO().getSelectedData()

    // CoinPrice

    fun getAllCoinPriceData() = db.selectedCoinDAO().getAllData()

    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) = db.selectedCoinDAO().insert(selectedCoinPriceEntity)

    fun getOneSelectedCoinData(coinName: String) = db.selectedCoinDAO().getOneCoinData(coinName)

}
