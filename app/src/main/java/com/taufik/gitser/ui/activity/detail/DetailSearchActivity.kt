package com.taufik.gitser.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.taufik.gitser.databinding.ActivityDetailSearchBinding

class DetailSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}