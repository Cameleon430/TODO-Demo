package com.example.todo_demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fab: View = findViewById(R.id.floating_button)
        val groupDetailViewButton : View = findViewById(R.id.groupDetailViewButton)

        groupDetailViewButton.setOnClickListener {
            val intent = Intent(this, GroupDetailActivity::class.java)
            startActivity(intent)
        }

        fab.setOnClickListener{
            Toast.makeText(applicationContext, "Hey, you clicked it!", Toast.LENGTH_SHORT).show()
        }
    }

}