package com.example.idols

    import android.content.Intent
    import android.os.Bundle
    import android.widget.ImageView
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 1. 화면 클릭 감지
        // 아이디로 특정 이미지 뷰를 찾음
        val image1 = findViewById<ImageView>(R.id.image1)
        val image2 = findViewById<ImageView>(R.id.image2)
        val image3 = findViewById<ImageView>(R.id.image3)
        val image4 = findViewById<ImageView>(R.id.image4)
        val image5 = findViewById<ImageView>(R.id.image5)
        val image6 = findViewById<ImageView>(R.id.image6)
        val image7 = findViewById<ImageView>(R.id.image7)

        // 클릭시 수행되는 코드
        image1.setOnClickListener {
            // 2. 다음 화면으로 네비게이팅
            val intent = Intent(this, ImageInsideActivity::class.java)
            // 파라미터 넘겨주기
            intent.putExtra("data", "1")
            startActivity(intent)

        }

        image2.setOnClickListener {
            // 2. 다음 화면으로 네비게이팅
            val intent = Intent(this, ImageInsideActivity::class.java)
            intent.putExtra("data", "2")
            startActivity(intent)

        }

        image3.setOnClickListener {
            // 2. 다음 화면으로 네비게이팅
            val intent = Intent(this, ImageInsideActivity::class.java)
            intent.putExtra("data", "3")
            startActivity(intent)

        }

        image4.setOnClickListener {
            // 2. 다음 화면으로 네비게이팅
            val intent = Intent(this, ImageInsideActivity::class.java)
            intent.putExtra("data", "4")
            startActivity(intent)

        }

        image5.setOnClickListener {
            // 2. 다음 화면으로 네비게이팅
            val intent = Intent(this, ImageInsideActivity::class.java)
            intent.putExtra("data", "5")
            startActivity(intent)

        }

        image6.setOnClickListener {
            // 2. 다음 화면으로 네비게이팅
            val intent = Intent(this, ImageInsideActivity::class.java)
            intent.putExtra("data", "6")
            startActivity(intent)

        }

        image7.setOnClickListener {
            // 2. 다음 화면으로 네비게이팅
            val intent = Intent(this, ImageInsideActivity::class.java)
            intent.putExtra("data", "7")
            startActivity(intent)

        }

    }
}