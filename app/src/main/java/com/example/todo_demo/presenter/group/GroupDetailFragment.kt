package com.example.todo_demo.presenter.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.todo_demo.R
import com.example.todo_demo.databinding.FragmentGroupDetailBinding
import com.example.todo_demo.presenter.task.TaskDetailFragment

class GroupDetailFragment : Fragment(R.layout.fragment_group_detail){

    //region Properties

    private lateinit var binding: FragmentGroupDetailBinding

    //endregion

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onSetTitle()
        initializeListeners()
    }


    //endregion

    //region Actions

    private fun initializeListeners(){
        binding.taskDetailViewButton.setOnClickListener {
            onNavigateTaskDetailFragment()
        }
    }

    private fun onSetTitle(){
        val titleTemplate = getString(R.string.group_detail_title_template)
        val groupId = arguments?.getInt(GROUP_ID, DEFAULT_VALUE)
        val title = "$titleTemplate $groupId"

        binding.groupTitleTextView.text = title
    }

    private fun onNavigateTaskDetailFragment() {
        val fragment = TaskDetailFragment.newInstance(1)
        parentFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    //endregion

    //region Nested

    companion object{
        private const val DEFAULT_VALUE = -1
        private const val GROUP_ID = "GROUP_ID"

        fun newInstance(groupId: Int): GroupDetailFragment {
            return GroupDetailFragment().apply{
                arguments = bundleOf(GROUP_ID to groupId)
            }
        }
    }

    //endregion

}