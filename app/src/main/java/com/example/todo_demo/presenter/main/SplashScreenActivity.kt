package com.example.todo_demo.presenter.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_demo.R

class SplashScreenActivity : AppCompatActivity() {

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        onStartMainActivity()
    }

    //endregion

    //region Actions

    private fun onStartMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DURATION)
    }

    //endregion

    //region Nested

    companion object {
        const val SPLASH_SCREEN_DURATION: Long = 2000
    }

    //endregion

}