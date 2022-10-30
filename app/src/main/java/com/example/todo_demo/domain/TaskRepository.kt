package com.example.todo_demo.domain

interface TaskRepository {

    fun getAll(groupID: Int): List<Task>

    fun get(groupID: Int, taskID: Int): Task?

    fun add(groupID: Int, task: Task): Int

    fun update(groupID: Int, updatedTask: Task)

    fun delete(groupID: Int, taskID: Int)

}