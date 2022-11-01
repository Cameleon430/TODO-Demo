package com.example.todo_demo.data

import com.example.todo_demo.domain.Task
import com.example.todo_demo.domain.TaskRepository

class InMemoryTaskRepository : TaskRepository {

    private val tasks = mutableMapOf<Int, MutableList<Task>>()

    override fun getAll(groupID: Int): List<Task> {
        return tasks[groupID] ?: emptyList()
    }

    override fun get(groupID: Int, taskID: Int): Task? {
        val tasksInGroup = tasks[groupID] ?: return null
        return tasksInGroup.firstOrNull{ it.id == taskID}
    }

    override fun update(groupID: Int, updatedTask: Task) {
        val tasksInGroup = tasks[groupID] ?: return
        val oldTask = tasksInGroup.firstOrNull{ it.id == updatedTask.id}
        val indexOf = tasksInGroup.indexOf(oldTask)
        tasksInGroup.removeAt(indexOf)
        tasksInGroup.add(indexOf, updatedTask)

    }

    override fun add(groupID: Int, task: Task): Int {
        val tasksInGroup = tasks[groupID] ?: mutableListOf()
        val updatedTask = task.copy(id = tasksInGroup.size)

        tasksInGroup.add(updatedTask)
        tasks[groupID] = tasksInGroup

        return updatedTask.id
    }

    override fun delete(groupID: Int, taskID: Int) {
        val tasksInGroup = tasks[groupID] ?: return
        tasksInGroup.removeAll { it.id == taskID }
    }
}