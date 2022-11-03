package com.example.todo_demo.presenter.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_demo.di.ServiceLocator
import com.example.todo_demo.domain.Task
import com.example.todo_demo.presenter.base.SingleLiveEvent
import com.example.todo_demo.presenter.base.TaskViewState
import kotlinx.coroutines.launch

class GroupDetailViewModel : ViewModel() {

    //region Properties

    private val groupRepository = ServiceLocator.provideGroupRepository()
    private val taskRepository = ServiceLocator.provideTaskRepository()

    private val groupNameMutable = MutableLiveData("")
    val groupName: LiveData<String> = groupNameMutable

    private val viewItemsMutable = MutableLiveData<List<TaskViewState>>(emptyList())
    val viewItems: LiveData<List<TaskViewState>> = viewItemsMutable

    private val actionStateMutable = SingleLiveEvent<ActionState>(ActionState.None)
    val actionState: LiveData<ActionState> = actionStateMutable

    private var groupID: Int = 0

    //endregion

    //region Actions

    fun onUpdateViewState(id: Int){
        viewModelScope.launch {
            val group = groupRepository.get(id) ?: throw IllegalStateException()
            groupNameMutable.value = group.name
            groupID = id
            invalidateViewItem()
        }
    }

    fun onNavigateTaskDetailFragment(task: TaskViewState) {
        val viewItem = viewItems.value?.firstOrNull{
            it.id == task.id
        }?: throw IllegalStateException()

        actionStateMutable.value = ActionState.TaskDetailView(viewItem.id)
    }

    fun onBackButtonPressed() {
        actionStateMutable.value = ActionState.Back
    }

    fun onCreateTask() {
        viewModelScope.launch {
            val task = Task(
                groupID = groupID,
                name = "",
                description = ""
            )
            val taskID = taskRepository.add(task)
            actionStateMutable.value = ActionState.TaskDetailView(taskID)
        }
    }

    private suspend fun invalidateViewItem(){
        val viewItems = taskRepository.getAllByGroupID(groupID).map{
            it.toViewState()
        }

        viewItemsMutable.value = viewItems
    }

    //endregion

    //region Nested

    sealed class ActionState{
        object None: ActionState()
        object Back: ActionState()
        object ShowMessage: GroupDetailViewModel.ActionState()
        data class TaskDetailView(val taskID: Int): ActionState()
    }

    private fun Task.toViewState(): TaskViewState {
        return TaskViewState(
            id = id,
            name = name
        )
    }

    //endregion
}