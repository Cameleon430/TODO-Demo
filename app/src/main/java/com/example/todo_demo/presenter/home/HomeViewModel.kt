package com.example.todo_demo.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_demo.di.ServiceLocator
import com.example.todo_demo.domain.Group
import com.example.todo_demo.presenter.base.GroupViewState
import com.example.todo_demo.presenter.base.SingleLiveEvent

class HomeViewModel : ViewModel() {

    //region Properties

    private val groupRepository = ServiceLocator.provideGroupRepository()

    private val actionStateMutable = SingleLiveEvent<ActionState>(ActionState.None)
    val actionState: LiveData<ActionState> = actionStateMutable

    private val viewItemsMutable = MutableLiveData<List<GroupViewState>>(emptyList())
    val viewItems: LiveData<List<GroupViewState>> = viewItemsMutable

    //endregion

    //region Actions

    fun onUpdateViewState(){
        invalidateViewItems()
    }

    fun onCreateGroupDialog(){
        actionStateMutable.value = ActionState.ShowCreateGroupDialog
    }

    fun onCreateGroup(name: String){
        val group = Group(name = name)

        groupRepository.add(group)
        invalidateViewItems()
    }

    fun onGroupDetailView(group: GroupViewState){

        val viewItem = viewItems.value?.firstOrNull{
            it.id == group.id
        }?: throw IllegalStateException()

        actionStateMutable.value = ActionState.GroupDetailView(viewItem.id)
    }

    private fun invalidateViewItems() {
        val groups = groupRepository.getAll()
        val viewItems = groups.map{
            it.toViewState()
        }
        viewItemsMutable.value = viewItems
    }

    //endregion

    //region Nested

    sealed class ActionState{
        object None: ActionState()
        object ShowCreateGroupDialog: ActionState()
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