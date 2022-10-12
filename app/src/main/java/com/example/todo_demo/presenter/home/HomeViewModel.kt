package com.example.todo_demo.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    //region Properties

    private val actionStateMutable = MutableLiveData<ActionState>(ActionState.None)
    val actionState: LiveData<ActionState> = actionStateMutable

    //endregion

    //region Actions

    fun onCreateGroup(){
        actionStateMutable.value = ActionState.ShowMessage
        actionStateMutable.value = ActionState.None
    }

    fun onGroupDetailView(){
        actionStateMutable.value = ActionState.GroupDetailView(1)
        actionStateMutable.value = ActionState.None
    }

    //endregion

    //region Nested

    sealed class ActionState{
        object None: ActionState()
        object ShowMessage: ActionState()
        data class GroupDetailView(val groupID: Int): ActionState()
    }

    //endregion

}