package com.example.todo_demo.presenter.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskDetailViewModel : ViewModel() {

    //region Properties

    private val taskIDMutable = MutableLiveData(-1)
    val taskID: LiveData<Int> = taskIDMutable

    private val taskTitleMutable = MutableLiveData("")
    val taskTitle: LiveData<String> = taskTitleMutable

    private val taskDescriptionMutable = MutableLiveData("")
    val taskDescription: LiveData<String> = taskDescriptionMutable

    private val actionStateMutable = MutableLiveData<ActionState>(ActionState.None)
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
        taskIDMutable.value = taskID
    }

    fun saveTask() {
        actionStateMutable.value = ActionState.ShowMessage
        actionStateMutable.value = ActionState.None
    }

    //endregion

    //region Nested

    sealed class ActionState{
        object None: ActionState()
        object ShowMessage: ActionState()
        data class OnTitleChanged(val taskTitle:String): ActionState()
        data class OnDescriptionChanged(val taskDescription:String): ActionState()
    }

    //endregion

}