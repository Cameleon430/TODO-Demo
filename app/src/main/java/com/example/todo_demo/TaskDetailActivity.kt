package com.example.todo_demo

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.properties.Delegates

class TaskDetailActivity : AppCompatActivity(){

    private lateinit var taskTitleEditText: EditText
    private lateinit var taskDescriptionEditText: EditText
    private lateinit var saveTaskButton: FloatingActionButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var taskID by Delegates.notNull<Int>()


    companion object{
        const val STORAGE_NAME_KEY = "MY_TASKS"
        const val TASK_TITLE_KEY = "TASK_TITLE"
        const val TASK_DESCRIPTION_KEY = "TASKS_DESCRIPTION"
        const val TASK_ID_KEY = "TASK_ID"
        const val TASK_ID_VAL = 1

        const val EXTRA_NAME_KEY = "TASK_NUM"
        const val DEFAULT_VAL = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        taskTitleEditText = findViewById(R.id.taskTitleEditText)
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText)
        saveTaskButton = findViewById(R.id.saveTaskButton)

        sharedPreferences = getSharedPreferences(STORAGE_NAME_KEY, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        taskID = intent.getIntExtra(EXTRA_NAME_KEY, DEFAULT_VAL)

        saveTaskButton.setOnClickListener{
            saveData()
        }

        setData()
    }

    private fun setData(){
        val savedID = sharedPreferences.getInt(TASK_ID_KEY, DEFAULT_VAL)

        if(savedID == taskID) {
            taskTitleEditText.setText(sharedPreferences.getString(TASK_TITLE_KEY, ""))
            taskDescriptionEditText.setText(sharedPreferences.getString(TASK_DESCRIPTION_KEY, ""))
        }
    }

    private fun saveData(){
        editor.putString(TASK_TITLE_KEY ,taskTitleEditText.text.toString())
        editor.putString(TASK_DESCRIPTION_KEY, taskDescriptionEditText.text.toString())
        editor.putInt(TASK_ID_KEY, TASK_ID_VAL)
        editor.apply()

        finish()
    }

    override fun onBackPressed() {
        if(hasUnsavedData()){
            showUnsavedDataMessage()
        }else {
            super.onBackPressed()
        }
    }

    private fun hasUnsavedData(): Boolean {
        val taskTitleText: String = taskTitleEditText.text.toString()
        val taskDescriptionText: String = taskDescriptionEditText.text.toString()

        return taskTitleText != sharedPreferences.getString(TASK_TITLE_KEY, "") ||
            taskDescriptionText != sharedPreferences.getString(TASK_DESCRIPTION_KEY, "")
    }

    private fun showUnsavedDataMessage(){
        Toast.makeText(applicationContext, "You have unsaved changes!", Toast.LENGTH_SHORT).show()
    }

}