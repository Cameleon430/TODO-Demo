package com.example.todo_demo.domain

data class Group(
    val id: Int = 0,
    val name: String
)

data class Task(
    val id: Int = 0,
    val name: String = "",
    val description: String = ""
)