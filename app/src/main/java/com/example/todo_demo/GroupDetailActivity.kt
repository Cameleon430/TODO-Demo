package com.example.todo_demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class GroupDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_detail)

        val taskDetailViewButton : View = findViewById(R.id.taskDetailViewButton)

        taskDetailViewButton.setOnClickListener {
            val intent = Intent(this, TaskDetailActivity::class.java)
            startActivity(intent)
        }
    }
}