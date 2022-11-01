package com.example.todo_demo.presenter.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_demo.di.ServiceLocator
import com.example.todo_demo.domain.Task
import com.example.todo_demo.presenter.base.SingleLiveEvent

class TaskDetailViewModel : ViewModel() {

    //region Properties

    private lateinit var task: Task
    private var groupID: Int = 0

    private val taskRepository = ServiceLocator.provideTaskRepository()

    private val taskTitleMutable = MutableLiveData("")
    val taskTitle: LiveData<String> = taskTitleMutable

    private val taskDescriptionMutable = MutableLiveData("")
    val taskDescription: LiveData<String> = taskDescriptionMutable

    private val actionStateMutable = SingleLiveEvent<ActionState>(ActionState.None)
    val actionState: LiveData<ActionState> = actionStateMutable

    //endregion

    //region Actions

    fun onTitleChanged(taskTitle: String){
        taskTitleMutable.value = taskTitle
    }

    fun onDescriptionChanged(taskDescription: String){
        taskDescriptionMutable.value = taskDescription
    }

    fun onUpdateViewState(groupID: Int, taskID: Int) {
        task = taskRepository.get(groupID, taskID) ?: throw IllegalStateException()

        taskTitleMutable.value = task.name
        taskDescriptionMutable.value = task.description
        this.groupID = groupID
    }

    fun saveTask() {
        val title = taskTitleMutable.value ?: ""
        val description = taskDescriptionMutable.value ?: ""
        val updatedTask = task.copy(name = title, description = description)
        taskRepository.update(groupID, updatedTask)
        onBackPressed()
    }

    fun onBackPressed(){
        actionStateMutable.value = ActionState.Back
    }

    fun deleteTask(){
        taskRepository.delete(groupID, task.id)
        onBackPressed()
    }

    //endregion

    //region Nested

    sealed class ActionState{
        object None: ActionState()
        object Back: ActionState()
        object ShowMessage: ActionState()
        data class OnTitleChanged(val taskTitle:String): ActionState()
        data class OnDescriptionChanged(val taskDescription:String): ActionState()
    }

    //endregion

}