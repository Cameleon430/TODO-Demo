package com.example.todo_demo.presenter.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.todo_demo.R
import com.example.todo_demo.databinding.FragmentTaskDetailBinding
import com.example.todo_demo.presenter.base.BaseFragment
import com.example.todo_demo.presenter.task.TaskDetailViewModel.ActionState

class TaskDetailFragment : BaseFragment() {

    //region Properties

    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel by viewModels<TaskDetailViewModel>()

    //endregion

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeViews()
        initializeListeners()
    }

    override fun onBackPressed() {
        viewModel.saveTask()
    }

    //endregion

    //region Initialization

    private fun initializeListeners(){
        with(binding){

            backButton.setOnClickListener{
                viewModel.saveTask()
            }

            saveTaskButton.setOnClickListener{
                viewModel.saveTask()
            }

            deleteButton.setOnClickListener{
                viewModel.deleteTask()
            }

            taskTitleEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onTitleChanged(text.toString())
            }

            taskDescriptionEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onDescriptionChanged(text.toString())
            }

        }
    }

    private fun initializeViews(){
        val groupID = arguments?.getInt(GROUP_ID) ?: throw IllegalStateException("Missing group id")
        val taskID = arguments?.getInt(TASK_ID) ?: throw IllegalStateException("Missing task id")

        viewModel.actionState.observe(viewLifecycleOwner, this::onActionStateChanged)
        viewModel.taskTitle.observe(viewLifecycleOwner, this::onTaskTitleChanged)
        viewModel.taskDescription.observe(viewLifecycleOwner, this::onTaskDescriptionChanged)

        viewModel.onUpdateViewState(groupID, taskID)
    }

    //endregion

    //region Actions

    private fun onTaskDescriptionChanged(taskDescription: String) {
        with(binding){
            if( taskDescriptionEditText.text.toString() != taskDescription){
                taskDescriptionEditText.setText(taskDescription)
            }
        }
    }

    private fun onTaskTitleChanged(taskTitle: String) {
        with(binding){
            if( taskTitleEditText.text.toString() != taskTitle){
                taskTitleEditText.setText(taskTitle)
            }
        }
    }

    private fun onActionStateChanged(state: ActionState) {
        when(state){
            ActionState.None -> {
                //This state ignored
            }
            ActionState.ShowMessage -> {
                showMessage()
            }
            is ActionState.OnDescriptionChanged -> {
                onTaskDescriptionChanged(state.taskDescription)
            }
            is ActionState.OnTitleChanged -> {
                onTaskTitleChanged(state.taskTitle)
            }
            ActionState.Back -> {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun showMessage() {
        Toast.makeText(context, getString(R.string.button_toast_template), Toast.LENGTH_SHORT).show()
    }

    //endregion

    //region Nested

    companion object{
        private const val TASK_ID = "TASK_ID"
        private const val GROUP_ID = "GROUP_ID"

        fun newInstance(groupID: Int, taskID: Int): TaskDetailFragment {
            return TaskDetailFragment().apply{
                arguments = bundleOf(
                    TASK_ID to taskID,
                    GROUP_ID to groupID
                )
            }
        }
    }

    //endregion

}