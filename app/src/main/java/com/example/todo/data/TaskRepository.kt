package com.example.todo.data

import com.example.todo.model.Priority
import com.example.todo.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull


class TaskRepository(private val dao: TaskDao) {

    // READ
    val tasks: Flow<List<Task>> = dao.getAllTasks()

    // CREATE
    suspend fun addTask(title: String, priority: Priority, dueDate: Long?) {
        val task = Task(title = title, priority = priority, dueDate = dueDate)
        dao.insert(task)
    }


    // UPDATE: toggle done
    suspend fun toggleDone(id: String) {
        val currentList = dao.getAllTasks().firstOrNull() ?: return
        val task = currentList.find { it.id == id } ?: return
        dao.update(task.copy(done = !task.done))
    }

    // DELETE
    suspend fun deleteAt(position: Int) {
        val currentList = dao.getAllTasks().firstOrNull() ?: return
        if (position !in currentList.indices) return
        val mutable = currentList.toMutableList()
        val removed = mutable.removeAt(position)
        dao.delete(removed)

        // reassign order
        val reordered = mutable.mapIndexed { index, t -> t.copy(order = index) }
        dao.updateTasks(reordered)
    }

    // MOVE (reorder)
    suspend fun move(from: Int, to: Int) {
        val currentList = dao.getAllTasks().firstOrNull() ?: return
        if (from !in currentList.indices || to !in currentList.indices) return

        val mutable = currentList.toMutableList()
        val item = mutable.removeAt(from)
        mutable.add(to, item)

        val reordered = mutable.mapIndexed { index, t -> t.copy(order = index) }
        dao.updateTasks(reordered)
    }
}
