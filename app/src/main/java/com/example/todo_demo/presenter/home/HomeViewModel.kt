package com.example.todo_demo.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todo_demo.domain.Group
import com.example.todo_demo.presenter.base.GroupViewState
import com.example.todo_demo.presenter.base.SingleLiveEvent

class HomeViewModel : ViewModel() {

    //region Properties

    private val actionStateMutable = SingleLiveEvent<ActionState>(ActionState.None)
    val actionState: LiveData<ActionState> = actionStateMutable

    private val viewItemsMutable = SingleLiveEvent<List<GroupViewState>>(emptyList())
    val viewItems: LiveData<List<GroupViewState>> = viewItemsMutable

    //endregion

    //region Actions

    fun onUpdateViewState(){
        val viewItems = createGroups().map{
            it.toViewState()
        }
        viewItemsMutable.value = viewItems
    }

    private fun createGroups(count: Int = 25): List<Group>{
        return (0..count).map { index ->
            Group(id = index, name = "Group $index")
        }.toList()
    }

    fun onCreateGroups(){
        actionStateMutable.value = ActionState.ShowMessage
    }

    fun onGroupDetailView(group: GroupViewState){

        val viewItem = viewItems.value?.firstOrNull{
            it.id == group.id
        }?: throw IllegalStateException()

        actionStateMutable.value = ActionState.GroupDetailView(viewItem.id)
    }

    //endregion

    //region Nested

    sealed class ActionState{
        object None: ActionState()
        object ShowMessage: ActionState()
        data class GroupDetailView(val groupID: Int): ActionState()
    }

    private fun Group.toViewState(): GroupViewState{
        return GroupViewState(
            id = id,
            name = name
        )
    }

    //endregion

}