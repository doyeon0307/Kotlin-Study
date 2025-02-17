package com.example.coco.receiver

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.impl.utils.ForceStopRunnable.BroadcastReceiver
import com.example.coco.service.PriceForegroundService

@SuppressLint("RestrictedApi")
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent?.action.equals("android.intent.action.BOOT_COMPLETED")) {

            val foreground = Intent(context, PriceForegroundService::class.java)
            foreground.action = "START"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(foreground)
            } else {
                context.startService(foreground)
            }

        }

    }

}