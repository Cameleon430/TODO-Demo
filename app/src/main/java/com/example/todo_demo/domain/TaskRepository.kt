package com.example.todo_demo.domain

interface TaskRepository {

    suspend fun getAllByGroupID(groupID: Int): List<Task>

    suspend fun get(taskID: Int): Task?

    suspend fun add(task: Task): Int

    suspend fun update(updatedTask: Task)

    suspend fun delete(taskID: Int)

}