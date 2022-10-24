package com.example.todo_demo.presenter.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todo_demo.domain.Task
import com.example.todo_demo.presenter.base.SingleLiveEvent
import com.example.todo_demo.presenter.base.TaskViewState

class GroupDetailViewModel : ViewModel() {

    //region Properties

    private val groupIDMutable = SingleLiveEvent(-1)
    val groupID: LiveData<Int> = groupIDMutable

    private val actionStateMutable = SingleLiveEvent<ActionState>(ActionState.None)
    val actionState: LiveData<ActionState> = actionStateMutable

    private val viewItemsMutable = SingleLiveEvent<List<TaskViewState>>(emptyList())
    val viewItems: LiveData<List<TaskViewState>> = viewItemsMutable

    //endregion

    //region Actions

    fun onUpdateViewState(groupID: Int){
        val viewItems = createTasks().map{
            it.toViewState()
        }
        viewItemsMutable.value = viewItems
        groupIDMutable.value = groupID
    }

    private fun createTasks(count: Int = 25): List<Task>{
        return (0..count).map { index ->
            Task(id = index, name = "Task $index")
        }.toList()
    }

    fun onCreateTask(){
        actionStateMutable.value = ActionState.ShowMessage
    }

    fun onNavigateTaskDetailFragment(task: TaskViewState) {
        val viewItem = viewItems.value?.firstOrNull{
            it.id == task.id
        }?: throw IllegalStateException()

        actionStateMutable.value = ActionState.NavigateTaskDataFragment(viewItem.id)
    }

    //endregion

    //region Nested

    sealed class ActionState{
        object None: ActionState()
        object ShowMessage: GroupDetailViewModel.ActionState()
        data class NavigateTaskDataFragment(val taskID: Int): ActionState()
    }

    private fun Task.toViewState(): TaskViewState {
        return TaskViewState(
            id = id,
            name = name
        )
    }

    //endregion
}