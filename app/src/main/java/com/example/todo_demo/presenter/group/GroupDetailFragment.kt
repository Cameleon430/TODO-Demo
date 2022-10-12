package com.example.todo_demo.presenter.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.todo_demo.R
import com.example.todo_demo.databinding.FragmentGroupDetailBinding
import com.example.todo_demo.presenter.group.GroupDetailViewModel.ActionState
import com.example.todo_demo.presenter.task.TaskDetailFragment

class GroupDetailFragment : Fragment(R.layout.fragment_group_detail){

    //region Properties

    private lateinit var binding: FragmentGroupDetailBinding
    private val viewModel by viewModels<GroupDetailViewModel>()

    //endregion

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeViewModel()
        initializeListeners()
    }


    //endregion

    //region Initialization

    private fun initializeViewModel(){
        val groupID = arguments?.getInt(GROUP_ID) ?: throw IllegalStateException("Missing group id")
        viewModel.onUpdateViewState(groupID)

        viewModel.actionState.observe(viewLifecycleOwner, this::onActionStateChanged)
        viewModel.groupID.observe(viewLifecycleOwner, this::onSetTitle)
    }

    private fun initializeListeners(){
        binding.taskDetailViewButton.setOnClickListener {
            viewModel.onNavigateTaskDetailFragment()
        }
    }

    //endregion

    //region Actions

    private fun onSetTitle(groupID: Int){
        val titleTemplate = getString(R.string.group_detail_title_template)
        val title = "$titleTemplate $groupID"

        binding.groupTitleTextView.text = title
    }

    private fun onNavigateTaskDetailFragment(taskID: Int) {
        val fragment = TaskDetailFragment.newInstance(taskID)
        parentFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun onActionStateChanged(state: ActionState){
        when(state){
            is ActionState.NavigateTaskDataFragment -> {
                onNavigateTaskDetailFragment(state.taskID)
            }
            ActionState.None -> {
                //This case ignored
            }
        }
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