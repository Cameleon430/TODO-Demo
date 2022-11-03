package com.example.todo_demo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GroupEntity::class, TaskEntity::class],
    version = 1
)

abstract class ToDoDatabase:RoomDatabase(){
    abstract fun provideGroupDao():GroupDao

    abstract fun provideTaskDao():TaskDao
}