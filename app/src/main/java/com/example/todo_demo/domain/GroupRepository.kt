package com.example.todo_demo.domain

interface GroupRepository {

    suspend fun getAll(): List<Group>

    suspend fun get(id: Int): Group?

    suspend fun  add(group: Group)

    suspend fun update(group: Group)

    suspend fun delete(group: Group)

}