package com.example.coco.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.coco.background.GetCoinPriceRecentContractedWorkManager
import com.example.coco.view.main.MainActivity
import com.example.coco.databinding.ActivitySelectBinding
import com.example.coco.view.adapter.SelectRVAdapter
import timber.log.Timber
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.TimeUnit

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding

    private val viewModel: SelectViewModel by viewModels()

    private lateinit var selectRVAdapter: SelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)


        try {
            selectRVAdapter = SelectRVAdapter(this, emptyList())

            binding.coinListRV.layoutManager = LinearLayoutManager(this)
            binding.coinListRV.adapter = selectRVAdapter

            try {
                viewModel.getCurrentCoinList()
            } catch (e: InvocationTargetException) {
                Timber.e("InvocationTargetException: ${e.cause?.message}")
                e.cause?.printStackTrace()  // 실제 원인 예외 출력
            }


            viewModel.currentPriceResult.observe(this) {
                selectRVAdapter.updateData(it)
            }

            binding.laterTextArea.setOnClickListener {

                viewModel.setUpFirstFlag()

                viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)

            }

            viewModel.save.observe(this) {
                if (it.equals("done")) {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }
            }

            saveInterestCoinDataPeriodic()

        } catch (e: java.lang.Exception) {
            Timber.e(e.toString())
        }

    }

    private fun saveInterestCoinDataPeriodic() {

        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentContractedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentContractedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )

    }

}