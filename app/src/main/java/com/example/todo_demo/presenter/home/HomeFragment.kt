package com.example.todo_demo.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.todo_demo.R
import com.example.todo_demo.databinding.FragmentHomeBinding
import com.example.todo_demo.presenter.base.GroupViewState
import com.example.todo_demo.presenter.group.GroupDetailFragment
import com.example.todo_demo.presenter.home.HomeViewModel.ActionState
import com.example.todo_demo.presenter.home.HomeViewModel.ActionState.*

class HomeFragment : Fragment(), GroupViewItemAdapter.OnItemSelectListener {

    //region Properties

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val adapter = GroupViewItemAdapter(this)

    //endregion

    //region Lifecycle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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
            groupRecyclerView.adapter = adapter
        }
    }

    private fun initializeViewModel(){
        viewModel.actionState.observe(viewLifecycleOwner, this::onActionStateChanged)
        viewModel.viewItems.observe(viewLifecycleOwner, this::onViewItemsChanged)
        viewModel.onUpdateViewState()
    }

    private fun initializeListeners(){
        with(binding){
            createGroupButton.setOnClickListener{
                viewModel.onCreateGroups()
            }
        }
    }

    //endregion

    //region Actions

    private fun onViewItemsChanged(groups: List<GroupViewState>) {
        adapter.updateItemsView(groups)
    }


    private fun onNavigateGroupDetailFragment( groupID: Int){
        val fragment = GroupDetailFragment.newInstance(groupID)
        parentFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun onActionStateChanged(state: ActionState){
        when(state){
            is GroupDetailView -> {
                onNavigateGroupDetailFragment(state.groupID)
            }
            None -> {
                //This case ignored
            }
            ShowMessage -> {
                Toast.makeText(context, getString(R.string.button_toast_template), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(group: GroupViewState) {
        viewModel.onGroupDetailView(group)
    }

    //endregion

}