package com.example.todo_demo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface GroupDao{

    @Query("SELECT * FROM GroupEntity")
    fun getAll(): List<GroupEntity>

    @Query("SELECT * FROM GroupEntity WHERE id == :id")
    fun get(id: Int): GroupEntity?

    @Insert(onConflict = REPLACE)
    fun insert(group: GroupEntity): Long

    @Query("DELETE FROM GroupEntity WHERE id == :id")
    fun delete(id: Int)
}

@Dao
interface TaskDao{

    @Query("SELECT * FROM TaskEntity WHERE groupID == :groupID")
    fun getAllByGroupID(groupID: Int): List<TaskEntity>

    @Query("SELECT * FROM TaskEntity WHERE id == :id")
    fun get(id: Int): TaskEntity?

    @Insert(onConflict = REPLACE)
    fun insert(task: TaskEntity): Long

    @Query("DELETE FROM TaskEntity WHERE id == :id")
    fun delete(id: Int)
}