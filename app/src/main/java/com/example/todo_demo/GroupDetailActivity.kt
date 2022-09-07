package com.example.todo_demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GroupDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_detail)

        val taskDetailViewButton : View = findViewById(R.id.taskDetailViewButton)
        val groupTitleTextView: TextView = findViewById(R.id.groupTitleTextView)
        val groupTitleText: String = "Group title " + intent.getIntExtra("GROUP_NUM", -1).toString()

        groupTitleTextView.text = groupTitleText

        taskDetailViewButton.setOnClickListener {
            val taskNumber = 1
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra("TASK_NUM", taskNumber)
            startActivity(intent)
        }
    }
}