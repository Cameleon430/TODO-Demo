package com.example.todo_demo.data

import com.example.todo_demo.domain.Task
import com.example.todo_demo.domain.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomTaskRepository(
    private val dao: TaskDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
):TaskRepository {

    override suspend fun getAllByGroupID(groupID: Int): List<Task> {
        return withContext(dispatcher) {
            dao.getAllByGroupID(groupID).map { it.toModel() }
        }
    }

    override suspend fun get(taskID: Int): Task? {
        return withContext(dispatcher) {
            dao.get(taskID)?.toModel()
        }
    }

    override suspend fun add(task: Task): Int {
        return withContext(dispatcher) {
            val updatedTask = task.copy(id = 0)
            val taskEntity = updatedTask.toEntity()
            dao.insert(taskEntity).toInt()
        }
    }

    override suspend fun update(updatedTask: Task) {
        withContext(dispatcher){
            val taskEntity = updatedTask.toEntity()
            dao.insert(taskEntity)
        }
    }

    override suspend fun delete(taskID: Int) {
        withContext(dispatcher){
            dao.delete(taskID)
        }
    }

    private fun TaskEntity.toModel(): Task {
        return Task(
            id = id,
            groupID = groupID,
            name = name,
            description = description
        )
    }

    private fun Task.toEntity(): TaskEntity{
        return TaskEntity(
            id = id,
            groupID = groupID,
            name = name,
            description = description
        )
    }
}