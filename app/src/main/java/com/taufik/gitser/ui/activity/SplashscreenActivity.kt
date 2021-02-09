package com.taufik.gitser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.taufik.gitser.databinding.ActivitySplashscreenBinding

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSplashscreen()
    }

    private fun setSplashscreen() {

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }, 1000)
    }
}