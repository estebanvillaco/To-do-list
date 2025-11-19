package com.example.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val priority: Priority,
    val done: Boolean = false,
    var order: Int = 0,
    val dueDate: Long? = null    // 👈 NEW
)
