package com.taufik.gitser.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.taufik.gitser.R
import com.taufik.gitser.databinding.ActivityMainBinding
import com.taufik.gitser.ui.activity.search.SearchActivity
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            
            R.id.nav_search_main -> {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_favorite -> Toasty.normal(this, "Favorite", R.drawable.ic_favorite).show()
            R.id.nav_settings -> Toasty.normal(this, "Settings", R.drawable.ic_settings).show()
            R.id.nav_profile -> Toasty.normal(this, "Profile", R.drawable.ic_person).show()
        }
        return super.onOptionsItemSelected(item)
    }
}