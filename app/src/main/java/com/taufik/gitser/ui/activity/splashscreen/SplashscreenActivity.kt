package com.taufik.gitser.ui.activity.splashscreen

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.taufik.gitser.databinding.ActivitySplashscreenBinding
import com.taufik.gitser.ui.activity.main.MainActivity
import com.taufik.gitser.utils.applyEdgeToEdgeInsets

class SplashscreenActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashscreenBinding.inflate(layoutInflater) }
    private val splashTime = 1000L
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        applyEdgeToEdgeInsets(window, binding.root)

        setSplashscreen()
        setAppVersion()
    }

    private fun setSplashscreen() {
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTime)
    }

    private fun setAppVersion() {
        binding.apply {
            try {
                val pInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
                val appVersion = pInfo.versionName
                tvAppVersion.text = appVersion
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}