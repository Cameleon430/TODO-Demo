package com.example.todo_demo.domain

interface GroupRepository {

    fun getAll(): List<Group>

    fun get(id: Int): Group?

    fun  add(group: Group)

    fun update(group: Group)

    fun delete(group: Group)

}