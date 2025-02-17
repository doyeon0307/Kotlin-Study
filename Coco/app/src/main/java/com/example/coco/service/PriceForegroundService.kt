package com.example.coco.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.coco.R
import com.example.coco.dataModel.CurrentPrice
import com.example.coco.dataModel.CurrentPriceResult
import com.example.coco.network.repository.NetworkRepository
import com.example.coco.view.main.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Random

class PriceForegroundService : Service() {

    private val NOTIFICATION_ID = 10000

    private val networkRepository = NetworkRepository()

    lateinit var job: Job

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {

            "START" -> {

                // job을 사용하지 않으면?
                // Foreground 서비스는 종료되지만 while문이 계속 돌아감
                // 3초마다 START 로그가 찍힘
                job = CoroutineScope(Dispatchers.Default).launch {

                    while(true) {
                        Timber.d("START")
                        startForeground(NOTIFICATION_ID, makeNotification())

                        delay(3000)
                    }
                }

            }

            "STOP" -> {

                Timber.d("STOP")

                job.cancel()
                stopForeground(true)
                stopSelf()

            }

        }

        return START_STICKY

    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    private suspend fun makeNotification(): Notification {

        val result = getAllCoinList()

        val randomNum = Random().nextInt(result.size)

        val title = result[randomNum].coinName
        val content = result[randomNum].coinInfo.fluctate_24H

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "name"
            val descriptionText = "descriptionText"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
            .setContentTitle("코인 이름 : $title")
            .setContentText("변동 가격 : $content")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        return builder.build()

    }

    suspend fun getAllCoinList(): ArrayList<CurrentPriceResult> {

        val result = networkRepository.getCurrentCurrentCoinList()

        val currentPriceResultList = ArrayList<CurrentPriceResult>()

        for (coin in result.data) {

            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data[coin.key])
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)

                currentPriceResultList.add(currentPriceResult)

            } catch (e: java.lang.Exception) {
                Timber.d(e.toString())
            }

        }

        return currentPriceResultList
    }

}