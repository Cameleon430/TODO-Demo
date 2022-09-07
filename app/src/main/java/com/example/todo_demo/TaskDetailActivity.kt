package com.example.todo_demo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TaskDetailActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val taskTitleTextView: TextView = findViewById(R.id.taskTitleTextView)
        val taskTitleText: String = "Task title " + intent.getIntExtra("TASK_NUM", -1).toString()

        taskTitleTextView.text = taskTitleText
    }
}