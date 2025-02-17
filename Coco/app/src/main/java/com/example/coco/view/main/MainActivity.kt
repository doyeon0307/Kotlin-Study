package com.example.coco.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupWithNavController
import com.example.coco.R

import com.example.coco.databinding.ActivityMainBinding
import com.example.coco.view.setting.SettingActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setting.setOnClickListener {

            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)

        }

        val bottomNavigationView = binding.bottomNavigationView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? androidx.navigation.fragment.NavHostFragment
        val navController = navHostFragment?.navController
//        val navController = findNavController(R.id.fragmentContainerView)

        if (navController != null) {
            bottomNavigationView.setupWithNavController(navController)
        } else {
            Timber.e("NavController is null")
        }

    }
}