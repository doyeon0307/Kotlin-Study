package com.example.coco.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coco.dataModel.CurrentPrice
import com.example.coco.dataModel.CurrentPriceResult
import com.example.coco.dataStore.MyDataStore
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.network.repository.DBRepository
import com.example.coco.network.repository.NetworkRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val networkRepository = NetworkRepository()
    private val dbRepository = DBRepository()

    private var currentPriceResultList: ArrayList<CurrentPriceResult> = arrayListOf()

    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    private val _saved = MutableLiveData<String>()
    val save: LiveData<String>
        get() = _saved

    fun getCurrentCoinList() = viewModelScope.launch {

        val result = networkRepository.getCurrentCurrentCoinList()

        currentPriceResultList = ArrayList()

        for (coin in result.data) {

            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)

                currentPriceResultList.add(currentPriceResult)

            } catch (e: java.lang.Exception) {
                Timber.d(e.toString())
            }

        }

        _currentPriceResult.value = currentPriceResultList

    }

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }

    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) = viewModelScope.launch(Dispatchers.IO) {

        // 1. 전체 코인 데이터 가져옴
        for (coin in currentPriceResultList) {

            val selected = selectedCoinList.contains(coin.coinName)

            // 2. 내가 선택한 코인인지 아닌지 구분
            val interestCoinEntity = InterestCoinEntity(
                0,
                coin.coinName,
                coin.coinInfo.opening_price,
                coin.coinInfo.closing_price,
                coin.coinInfo.min_price,
                coin.coinInfo.max_price,
                coin.coinInfo.units_traded,
                coin.coinInfo.acc_trade_value,
                coin.coinInfo.prev_closing_price,
                coin.coinInfo.units_traded_24H,
                coin.coinInfo.acc_trade_value_24H,
                coin.coinInfo.fluctate_24H,
                coin.coinInfo.fluctate_rate_24H,
                selected
            )

            // 3. 저장
            try {
                interestCoinEntity.let {
                    dbRepository.insertInterestCoinData(it)
                }
            } catch (e: java.lang.Exception) {
                Timber.e(e, "DB Insert Error")
                e.printStackTrace()
            }

            withContext(Dispatchers.Main) {
                _saved.value = "done"
            }
        }
    }

}