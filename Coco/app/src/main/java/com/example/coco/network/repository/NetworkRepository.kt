package com.example.coco.network.repository

import com.example.coco.network.Api
import com.example.coco.network.RetrofitInstance

class NetworkRepository {

    private var client = RetrofitInstance.getInstance().create(Api::class.java)

    suspend fun getCurrentCurrentCoinList() = client.getCurrentCoinList()

    suspend fun getInterestCoinPriceData(coin: String) = client.getCurrentCoinPrice(coin)

}