package com.example.todo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo.model.Priority
import com.example.todo.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter(
    private val onToggle: (String) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskVH>(DIFF) {
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
        }
    }

    inner class TaskVH(val b: ItemTaskBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        val b = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskVH(b)
    }

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        val item = getItem(position)

        holder.b.txtTitle.text = item.title

        // checkbox fix
        holder.b.checkDone.setOnCheckedChangeListener(null)
        holder.b.checkDone.isChecked = item.done
        holder.b.checkDone.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != item.done) onToggle(item.id)
        }

        // Priority
        holder.b.chipPriority.text = when (item.priority) {
            Priority.HIGH -> "HIGH"
            Priority.MEDIUM -> "MED"
            Priority.LOW -> "LOW"
        }

        // Due date
        holder.b.txtDueDate.text =
            item.dueDate?.let { "Due: ${dateFormatter.format(Date(it))}" }
                ?: "No due date"
    }


}
