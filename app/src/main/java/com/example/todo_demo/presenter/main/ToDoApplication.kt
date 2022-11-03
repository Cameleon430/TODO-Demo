package com.example.todo_demo.presenter.main

import android.app.Application
import com.example.todo_demo.di.ServiceLocator

class ToDoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        ServiceLocator.initializeDatabase(this)
    }
}