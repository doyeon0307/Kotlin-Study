package com.example.coco.view.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.coco.view.main.MainActivity
import com.example.coco.databinding.ActivityIntroBinding
import timber.log.Timber

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private val viewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.checkFirstFlag()
        viewModel.first.observe(this) {

            if (it) {
                // 처음 접속 X
                Timber.d("Not First")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // 처음 접속 O
                Timber.d("First")
                binding.animationView.visibility = View.INVISIBLE
                binding.fragmentContainerView.visibility = View.VISIBLE
            }

        }

    }
}