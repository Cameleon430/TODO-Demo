package com.example.todo_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class GroupDetailFragment : Fragment(R.layout.fragment_group_detail){

    //region Properties

    private lateinit var groupTitleTextView: TextView
    private lateinit var taskDetailViewButton: Button

    //endregion

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_group_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        taskDetailViewButton = view.findViewById(R.id.taskDetailViewButton)
        groupTitleTextView = view.findViewById(R.id.groupTitleTextView)

        onSetTitle()

        taskDetailViewButton.setOnClickListener {
            onNavigateTaskDetailFragment()
        }
    }


    //endregion

    //region Actions

    private fun onSetTitle(){
        val titleTemplate = getString(R.string.group_detail_title_template)
        val groupId = arguments?.getInt(GROUP_ID, DEFAULT_VALUE)
        val title = "$titleTemplate $groupId"

        groupTitleTextView.text = title
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

        fun newInstance(groupId: Int): GroupDetailFragment{
            return GroupDetailFragment().apply{
                arguments = bundleOf(GROUP_ID to groupId)
            }
        }
    }

    //endregion

}