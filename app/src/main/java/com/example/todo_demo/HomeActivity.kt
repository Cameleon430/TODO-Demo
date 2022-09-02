package com.example.todo_demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fab: View = findViewById(R.id.floating_button)

        fab.setOnClickListener{
            Toast.makeText(applicationContext, "Hey, you clicked it!", Toast.LENGTH_SHORT).show()
        }
    }

}