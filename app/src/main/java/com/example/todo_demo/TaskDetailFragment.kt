package com.example.todo_demo

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    //region Properties

    private lateinit var taskTitleEditText: EditText
    private lateinit var taskDescriptionEditText: EditText
    private lateinit var saveTaskButton: FloatingActionButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    //endregion

    //region Lifecycles

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskTitleEditText = view.findViewById(R.id.taskTitleEditText)
        taskDescriptionEditText = view.findViewById(R.id.taskDescriptionEditText)
        saveTaskButton = view.findViewById(R.id.saveTaskButton)

        val context = requireContext()
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME_KEY, AppCompatActivity.MODE_PRIVATE)

        saveTaskButton.setOnClickListener{
            saveData()
        }

        setData()
    }

    //endregion

    //region Functions

    private fun setData(){
        val savedID = sharedPreferences.getInt(SAVED_ID_KEY, DEFAULT_VALUE)
        val taskID = arguments?.getInt(TASK_ID, DEFAULT_VALUE)

        if(savedID == taskID) {
            taskTitleEditText.setText(sharedPreferences.getString(TASK_TITLE_KEY, ""))
            taskDescriptionEditText.setText(sharedPreferences.getString(TASK_DESCRIPTION_KEY, ""))
        }
    }

    private fun saveData(){
        if(hasUnsavedData()) {
            editor = sharedPreferences.edit()
            editor.putString(TASK_TITLE_KEY, taskTitleEditText.text.toString())
            editor.putString(TASK_DESCRIPTION_KEY, taskDescriptionEditText.text.toString())
            editor.putInt(SAVED_ID_KEY, SAVED_ID_VAL)
            editor.apply()

            requireActivity().onBackPressed()
        }
    }

    private fun hasUnsavedData(): Boolean {
        val taskTitleText: String = taskTitleEditText.text.toString()
        val taskDescriptionText: String = taskDescriptionEditText.text.toString()

        return taskTitleText.isNotBlank() &&
                taskDescriptionText.isNotBlank()
    }

    //endregion

    //region Nested

    companion object{
        private const val TASK_ID = "TASK_ID"
        private const val SAVED_ID_KEY = "TASK_ID_KEY"
        private const val SAVED_ID_VAL = 1
        private const val DEFAULT_VALUE = -1
        private const val STORAGE_NAME_KEY = "MY_TASKS"
        private const val TASK_DESCRIPTION_KEY = "TASK_DESCRIPTION_KEY"
        private const val TASK_TITLE_KEY = "TASK_TITLE_KEY"

        fun newInstance(taskId: Int): TaskDetailFragment{
            return TaskDetailFragment().apply{
                arguments = bundleOf(TASK_ID to taskId)
            }
        }
    }

    //endregion
}