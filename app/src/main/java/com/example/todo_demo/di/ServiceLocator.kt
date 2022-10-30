package com.example.todo_demo.di

import com.example.todo_demo.data.InMemoryGroupRepository
import com.example.todo_demo.data.InMemoryTaskRepository
import com.example.todo_demo.domain.GroupRepository
import com.example.todo_demo.domain.TaskRepository

object ServiceLocator {

    private val GroupRepository = InMemoryGroupRepository()
    private val TaskRepository = InMemoryTaskRepository()

    fun provideGroupRepository(): GroupRepository{
        return GroupRepository
    }

    fun provideTaskRepository(): TaskRepository{
        return TaskRepository
    }
}