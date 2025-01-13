package com.example.mysololife.contentsList

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.mysololife.R

class ContentShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_content_show)

        val getUrl = intent.getStringExtra("url")

        val webView: WebView = findViewById(R.id.webView)
        webView.loadUrl(getUrl.toString())

    }
}