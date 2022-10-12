package com.example.todo_demo.presenter.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GroupDetailViewModel : ViewModel() {

    //region Properties

    private val groupIDMutable = MutableLiveData(-1)
    val groupID: LiveData<Int> = groupIDMutable

    private val actionStateMutable = MutableLiveData<ActionState>(ActionState.None)
    val actionState: LiveData<ActionState> = actionStateMutable

    //endregion

    //region Actions

    fun onUpdateViewState(groupID: Int){
        groupIDMutable.value = groupID
    }

    fun onNavigateTaskDetailFragment() {
        actionStateMutable.value = ActionState.NavigateTaskDataFragment(1)
        actionStateMutable.value = ActionState.None
    }

    //endregion

    //region Nested

    sealed class ActionState{
        object None: ActionState()
        data class NavigateTaskDataFragment(val taskID: Int): ActionState()
    }

    //endregion
}