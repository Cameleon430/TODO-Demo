package com.example.todo_demo.di

import android.content.Context
import androidx.room.Room
import com.example.todo_demo.data.*
import com.example.todo_demo.domain.GroupRepository
import com.example.todo_demo.domain.TaskRepository

object ServiceLocator {

    private var groupRepository: GroupRepository? = null
    private var taskRepository: TaskRepository? = null
    private lateinit var database: ToDoDatabase

    fun initializeDatabase(context: Context){
        database = Room.databaseBuilder(context, ToDoDatabase::class.java, "to-do-data").build()
    }

    fun provideGroupRepository(): GroupRepository{
        if (groupRepository == null){
            val dao = provideGroupDao()
            groupRepository = RoomGroupRepository(dao)
        }
        return groupRepository!!
    }

    fun provideTaskRepository(): TaskRepository{
        if (taskRepository == null){
            val dao = provideTaskDao()
            taskRepository = RoomTaskRepository(dao)
        }
        return taskRepository!!
    }

    fun provideGroupDao(): GroupDao{
        return database.provideGroupDao()
    }

    fun provideTaskDao(): TaskDao{
        return database.provideTaskDao()
    }
}