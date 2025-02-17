package com.example.coco.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coco.dataModel.UpDownDataSet
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.network.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val dbRepository = DBRepository()

    lateinit var selectedCoinList: LiveData<List<InterestCoinEntity>>

    private val _arr15min = MutableLiveData<List<UpDownDataSet>>()
    val arr15min: LiveData<List<UpDownDataSet>>
        get() = _arr15min

    private val _arr30min = MutableLiveData<List<UpDownDataSet>>()
    val arr30min: LiveData<List<UpDownDataSet>>
        get() = _arr30min

    private val _arr45min = MutableLiveData<List<UpDownDataSet>>()
    val arr45min: LiveData<List<UpDownDataSet>>
        get() = _arr45min

    // CoinListFragment

    fun getAllInterestCoinData() = viewModelScope.launch {

        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList

    }

    // DB를 처리할 때는 Dispatchers 사용
    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = viewModelScope.launch(Dispatchers.IO) {

        interestCoinEntity.selected = !interestCoinEntity.selected

        dbRepository.updateInterestCoinData(interestCoinEntity)

    }

    // PriceChangeFragment

    fun getAllSelectedCoinData() = viewModelScope.launch(Dispatchers.IO) {

        // 관심 있는 코인 리스트 가져오기
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val arr15min = ArrayList<UpDownDataSet>()
        val arr30min = ArrayList<UpDownDataSet>()
        val arr45min = ArrayList<UpDownDataSet>()

        // 개별 코인의 가격 가져오기
        for (data in selectedCoinList) {

            val coinName = data.coin_name

            // 마지막 값이 최신값->reverse->첫 번째 값이 최신값
            val oneCoinData = dbRepository.getOneSelectedCoinData(coinName).reversed()

            val size = oneCoinData.size

            if (size > 1) {

                // 현재와 15분 전 비교
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr15min.add(upDownDataSet)

            }

            if (size > 2) {

                // 현재와 30분 전 비교
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr30min.add(upDownDataSet)

            }

            if (size > 3) {

                // 현재와 45분 전 비교
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr45min.add(upDownDataSet)

            }


        }

        withContext(Dispatchers.Main) {
            _arr15min.value = arr15min
            _arr30min.value = arr30min
            _arr45min.value = arr45min
        }

    }

}