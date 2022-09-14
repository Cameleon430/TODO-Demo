package com.example.todo_demo

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class TaskDetailActivity : AppCompatActivity(){

    private lateinit var taskTitleEditText: EditText
    private lateinit var taskDescriptionEditText: EditText

    companion object{
        const val KEY_TITLE = "TASK_TITLE"
        const val KEY_DESCRIPTION = "TASK_DESCRIPTION"
        const val EXTRA_NAME = "TASK_NUM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        taskTitleEditText = findViewById(R.id.taskTitleEditText)
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(KEY_TITLE, taskTitleEditText.text.toString())
        outState.putString(KEY_DESCRIPTION, taskDescriptionEditText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        taskTitleEditText.setText(savedInstanceState.getString(KEY_TITLE))
        taskDescriptionEditText.setText(savedInstanceState.getString(KEY_DESCRIPTION))
    }

}