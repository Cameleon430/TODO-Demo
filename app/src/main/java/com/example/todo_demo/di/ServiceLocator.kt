package com.example.todo_demo.di

import com.example.todo_demo.data.InMemoryGroupRepository
import com.example.todo_demo.data.InMemoryTaskRepository
import com.example.todo_demo.domain.GroupRepository
import com.example.todo_demo.domain.TaskRepository

object ServiceLocator {

    private val groupRepository = InMemoryGroupRepository()
    private val taskRepository = InMemoryTaskRepository()

    fun provideGroupRepository(): GroupRepository{
        return groupRepository
    }

    fun provideTaskRepository(): TaskRepository{
        return taskRepository
    }
}