package com.example.loopingviewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.loopingviewpager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

  private fun initViews(){
      navHostFragment =
          supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }
}