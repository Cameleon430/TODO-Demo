package com.example.todo_demo.data

import com.example.todo_demo.domain.Group
import com.example.todo_demo.domain.GroupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomGroupRepository(
    private val dao: GroupDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
): GroupRepository {
    override suspend fun getAll(): List<Group> {
        return withContext(dispatcher) {
            dao.getAll().map {
                it.toModel()
            }
        }
    }

    override suspend fun get(id: Int): Group? {
        return withContext(dispatcher) {
            dao.get(id)?.toModel()
        }
    }

    override suspend fun add(group: Group) {
        withContext(dispatcher) {
            val updatedGroup = group.copy(id = 0)
            dao.insert(updatedGroup.toEntity())
        }
    }

    override suspend fun update(group: Group) {
        withContext(dispatcher) {
            dao.insert(group.toEntity())
        }
    }

    override suspend fun delete(group: Group) {
        withContext(dispatcher) {
            dao.delete(group.id)
        }
    }

    private fun GroupEntity.toModel(): Group{
        return Group(
            id = id,
            name = name
        )
    }

    private fun Group.toEntity(): GroupEntity{
        return GroupEntity(
            id = id,
            name = name
        )
    }
}