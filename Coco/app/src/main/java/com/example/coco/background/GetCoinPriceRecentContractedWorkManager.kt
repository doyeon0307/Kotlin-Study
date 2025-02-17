package com.example.coco.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coco.db.entity.SelectedCoinPriceEntity
import com.example.coco.network.model.RecentCoinPriceList
import com.example.coco.network.repository.DBRepository
import com.example.coco.network.repository.NetworkRepository
import timber.log.Timber
import java.util.Date
import java.util.Calendar

// 1. 관심 있는 코인 목록 가져옴
// 2. 코인 각각의 가격 변동 가져옴 (New API)
// 3. DB에 저장

class GetCoinPriceRecentContractedWorkManager(val context: Context, workerParameter: WorkerParameters) : CoroutineWorker(context, workerParameter) {

    private val dbRepository = DBRepository()
    private val networkRepository = NetworkRepository()

    override suspend fun doWork(): Result {

        Timber.d("doWork")

        getAllInterestSelectedCoinData()

        return Result.success()

    }

    private suspend fun getAllInterestSelectedCoinData() {

        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val timeStamp = Calendar.getInstance().time

        for (coinData in selectedCoinList) {

            val recentCoinPriceList = networkRepository.getInterestCoinPriceData(coinData.coin_name)

            saveSelectedCoinPrice(
                coinData.coin_name,
                recentCoinPriceList,
                timeStamp
            )

        }

    }

    private suspend fun saveSelectedCoinPrice(
        coinName:String,
        recentCoinPriceList:RecentCoinPriceList,
        timeStamp: Date
    ) {

        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStamp
        )

        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)

    }

}