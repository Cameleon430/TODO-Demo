package com.example.todo_demo.presenter.task

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.todo_demo.R
import com.example.todo_demo.databinding.FragmentTaskDetailBinding

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    //region Properties

    private lateinit var binding: FragmentTaskDetailBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var taskID: Int = -1

    //endregion

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeViews()
        initializeListeners()
        onSetData()
    }

    //endregion

    //region Actions

    private fun initializeListeners(){
        binding.saveTaskButton.setOnClickListener{
            onSaveData()
        }
    }

    private fun initializeViews(){
        val context = requireContext()
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME_KEY, AppCompatActivity.MODE_PRIVATE)
    }

    private fun onSetData(){
        val savedID = sharedPreferences.getInt(SAVED_ID_KEY, DEFAULT_VALUE)
        taskID = arguments?.getInt(TASK_ID) ?: -1

        if(savedID == taskID) {
            binding.taskTitleEditText.setText(sharedPreferences.getString(TASK_TITLE_KEY, ""))
            binding.taskDescriptionEditText.setText(sharedPreferences.getString(TASK_DESCRIPTION_KEY, ""))
        }
    }

    private fun onSaveData(){
        if(hasUnsavedData()) {
            val editor = sharedPreferences.edit()
            editor.putString(TASK_TITLE_KEY, binding.taskTitleEditText.text.toString())
            editor.putString(TASK_DESCRIPTION_KEY, binding.taskDescriptionEditText.text.toString())
            editor.putInt(SAVED_ID_KEY, taskID)
            editor.apply()

            requireActivity().onBackPressed()
        }
    }

    private fun hasUnsavedData(): Boolean {
        val taskTitleText: String = binding.taskTitleEditText.text.toString()
        val taskDescriptionText: String = binding.taskDescriptionEditText.text.toString()

        return taskTitleText.isNotBlank() &&
                taskDescriptionText.isNotBlank()
    }

    //endregion

    //region Nested

    companion object{
        private const val TASK_ID = "TASK_ID"
        private const val SAVED_ID_KEY = "TASK_ID_KEY"
        private const val DEFAULT_VALUE = -1
        private const val STORAGE_NAME_KEY = "MY_TASKS"
        private const val TASK_DESCRIPTION_KEY = "TASK_DESCRIPTION_KEY"
        private const val TASK_TITLE_KEY = "TASK_TITLE_KEY"

        fun newInstance(taskId: Int): TaskDetailFragment {
            return TaskDetailFragment().apply{
                arguments = bundleOf(TASK_ID to taskId)
            }
        }
    }

    //endregion

}