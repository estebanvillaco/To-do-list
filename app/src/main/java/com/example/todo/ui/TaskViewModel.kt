package com.example.todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TaskRepository
import com.example.todo.model.Priority
import com.example.todo.model.Task
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks = repository.tasks
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addTask(title: String, prio: Priority, dueDate: Long?) = viewModelScope.launch {
        repository.addTask(title, prio, dueDate)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun toggleDone(id: String) = viewModelScope.launch {
        repository.toggleDone(id)
    }

    fun deleteAt(position: Int) = viewModelScope.launch {
        repository.deleteAt(position)
    }

    fun move(from: Int, to: Int) = viewModelScope.launch {
        repository.move(from, to)
    }
}
