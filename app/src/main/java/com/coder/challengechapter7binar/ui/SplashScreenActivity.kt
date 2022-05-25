package com.coder.challengechapter7binar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coder.challengechapter7binar.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.linearTv.alpha = 0f
        binding.linearTv.animate().setDuration(3000).alpha(1f).withEndAction {
            val intentKeMain = Intent(this, MainActivity::class.java)
            startActivity(intentKeMain)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}