package com.example.todo_demo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GroupDetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_NAME = "TASK_NUM"
        const val DEFAULT_VAL = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_detail)

        val taskDetailViewButton : View = findViewById(R.id.taskDetailViewButton)
        val groupTitleTextView: TextView = findViewById(R.id.groupTitleTextView)
        val groupTitleText: String = "Group title " + intent.getIntExtra(HomeActivity.EXTRA_NAME, DEFAULT_VAL).toString()

        groupTitleTextView.text = groupTitleText

        taskDetailViewButton.setOnClickListener {
            val taskNumber = 1
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra(EXTRA_NAME, taskNumber)
            startActivity(intent)
        }
    }
}