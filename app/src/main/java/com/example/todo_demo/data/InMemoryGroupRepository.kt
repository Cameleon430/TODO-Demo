package com.example.todo_demo.data

import com.example.todo_demo.domain.Group
import com.example.todo_demo.domain.GroupRepository

class InMemoryGroupRepository : GroupRepository {

    private val groups = mutableListOf<Group>()

    override fun getAll(): List<Group> {
        return groups
    }

    override fun get(id: Int): Group? {
        return groups.firstOrNull{ it.id == id}
    }

    override fun add(group: Group) {
        val updatedGroup = group.copy(id = groups.size)
        groups.add(updatedGroup)
    }

    override fun update(group: Group) {
        val oldGroup = get(group.id) ?: return
        val index = groups.indexOf(oldGroup)

        if(index >= 0){
            groups.removeAt(index)
            groups.add(index, group)
        }
        else return
    }

    override fun delete(group: Group) {
        val oldGroup = get(group.id) ?: return
        val index = groups.indexOf(oldGroup)

        if(index >= 0)
            groups.removeAt(index)
        else
            return
    }
}