package com.example.todo_demo.presenter.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_demo.di.ServiceLocator
import com.example.todo_demo.domain.Task
import com.example.todo_demo.presenter.base.SingleLiveEvent
import com.example.todo_demo.presenter.base.TaskViewState

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

    fun onUpdateViewState(groupID: Int){
        val group = groupRepository.get(groupID) ?: throw IllegalStateException()
        groupNameMutable.value = group.name
        this.groupID = groupID
        invalidateViewItem()
    }

    fun onNavigateTaskDetailFragment(task: TaskViewState) {
        val viewItem = viewItems.value?.firstOrNull{
            it.id == task.id
        }?: throw IllegalStateException()

        actionStateMutable.value = ActionState.TaskDetailView(groupID, viewItem.id)
    }

    fun onBackButtonPressed() {
        actionStateMutable.value = ActionState.Back
    }

    fun onCreateTask() {
        val task = Task()

        val taskID = taskRepository.add(groupID, task)
        actionStateMutable.value = ActionState.TaskDetailView(groupID, taskID)
    }

    private fun invalidateViewItem(){
        val viewItems = taskRepository.getAll(groupID).map{
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
        data class TaskDetailView(val groupID: Int, val taskID: Int): ActionState()
    }

    private fun Task.toViewState(): TaskViewState {
        return TaskViewState(
            id = id,
            name = name
        )
    }

    //endregion
}