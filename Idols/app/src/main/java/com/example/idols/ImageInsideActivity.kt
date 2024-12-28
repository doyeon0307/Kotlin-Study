package com.example.idols

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ImageInsideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_inside)

        val getData = intent.getStringExtra("data")
        val image = findViewById<ImageView>(R.id.imageArea)
        // Toast.makeText(this, getData, Toast.LENGTH_LONG).show()

        if (getData == "1") {
            image.setImageResource(R.drawable.image1)
        }
        if (getData == "2") {
            image.setImageResource(R.drawable.image2)
        }
        if (getData == "3") {
            image.setImageResource(R.drawable.image3)
        }
        if (getData == "4") {
            image.setImageResource(R.drawable.image4)
        }
        if (getData == "5") {
            image.setImageResource(R.drawable.image5)
        }
        if (getData == "6") {
            image.setImageResource(R.drawable.image6)
        }
        if (getData == "7") {
            image.setImageResource(R.drawable.image7)
        }

    }
}