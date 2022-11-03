package com.example.todo_demo.presenter.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_demo.di.ServiceLocator
import com.example.todo_demo.domain.Task
import com.example.todo_demo.presenter.base.SingleLiveEvent
import kotlinx.coroutines.launch

class TaskDetailViewModel : ViewModel() {

    //region Properties

    private lateinit var task: Task

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

    fun onUpdateViewState(taskID: Int) {
        viewModelScope.launch {
            task = taskRepository.get(taskID) ?: throw IllegalStateException()

            taskTitleMutable.value = task.name
            taskDescriptionMutable.value = task.description
        }
    }

    fun saveTask() {
        viewModelScope.launch {
            val title = taskTitleMutable.value ?: ""
            val description = taskDescriptionMutable.value ?: ""
            val updatedTask = task.copy(name = title, description = description)
            taskRepository.update(updatedTask)
            onBackPressed()
        }
    }

    private fun onBackPressed(){
        actionStateMutable.value = ActionState.Back
    }

    fun deleteTask(){
        viewModelScope.launch {
            taskRepository.delete(task.id)
            onBackPressed()
        }
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