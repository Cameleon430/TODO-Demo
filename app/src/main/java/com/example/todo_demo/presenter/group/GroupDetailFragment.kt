package com.example.todo_demo.presenter.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.todo_demo.R
import com.example.todo_demo.databinding.FragmentGroupDetailBinding
import com.example.todo_demo.presenter.base.TaskViewState
import com.example.todo_demo.presenter.group.GroupDetailViewModel.ActionState
import com.example.todo_demo.presenter.task.TaskDetailFragment

class GroupDetailFragment : Fragment(), TaskViewItemAdapter.OnItemSelectListener {

    //region Properties

    private lateinit var binding: FragmentGroupDetailBinding
    private val viewModel by viewModels<GroupDetailViewModel>()
    private val adapter = TaskViewItemAdapter(this)

    //endregion

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeViewModel()
        initializeListeners()
        initializeRecyclerView()
    }

    //endregion

    //region Initialization

    private fun initializeRecyclerView() {
        with(binding){
            taskRecyclerView.adapter = adapter
        }
    }

    private fun initializeViewModel(){
        val groupID = arguments?.getInt(GROUP_ID) ?: throw IllegalStateException("Missing group id")
        viewModel.onUpdateViewState(groupID)

        viewModel.actionState.observe(viewLifecycleOwner, this::onActionStateChanged)
        viewModel.groupName.observe(viewLifecycleOwner, this::onSetTitle)
        viewModel.viewItems.observe(viewLifecycleOwner, this::onViewItemsChanged)
    }

    private fun initializeListeners(){
        with(binding){
            backButton.setOnClickListener {
                viewModel.onBackButtonPressed()
            }

            createTaskButton.setOnClickListener{
                viewModel.onCreateTask()
            }
        }
    }

    //endregion

    //region Actions

    private fun onViewItemsChanged(tasks: List<TaskViewState>) {
        adapter.updateItemsView(tasks)
    }

    private fun onSetTitle(groupName: String){
        binding.groupTitleTextView.text = groupName
    }

    private fun onNavigateTaskDetailFragment(groupId: Int, taskID: Int) {
        val fragment = TaskDetailFragment.newInstance(groupID = groupId, taskID = taskID)
        parentFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun onActionStateChanged(state: ActionState){
        when(state){
            is ActionState.NavigateTaskDataFragment -> {
                onNavigateTaskDetailFragment(state.groupID, state.taskID)
            }
            ActionState.None -> {
                //This case ignored
            }
            ActionState.ShowMessage -> {
                Toast.makeText(context, getString(R.string.button_toast_template), Toast.LENGTH_SHORT).show()
            }
            ActionState.Back -> {
                parentFragmentManager.popBackStack()
            }
        }
    }


    override fun onClick(task: TaskViewState) {
        viewModel.onNavigateTaskDetailFragment(task)
    }


    //endregion

    //region Nested

    companion object{
        private const val GROUP_ID = "GROUP_ID"

        fun newInstance(groupId: Int): GroupDetailFragment {
            return GroupDetailFragment().apply{
                arguments = bundleOf(GROUP_ID to groupId)
            }
        }
    }
    //endregion

}