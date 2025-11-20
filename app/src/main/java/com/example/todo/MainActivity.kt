package com.example.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.DialogAddTaskBinding
import com.example.todo.model.Priority
import com.example.todo.model.Task
import com.example.todo.ui.TaskAdapter
import com.example.todo.ui.TaskViewModel
import com.example.todo.ui.TaskViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private val vm: TaskViewModel by viewModels { TaskViewModelFactory(application) }

    private var allTasks: List<Task> = emptyList()
    private var currentQuery: String = ""

    private enum class SortMode { BY_ORDER, PRIORITY_HIGH_FIRST, DUE_SOONEST_FIRST, DUE_LATEST_FIRST }
    private var currentSort = SortMode.BY_ORDER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val adapter = TaskAdapter(vm::toggleDone, this::showEditTaskDialog)
        b.recycler.layoutManager = LinearLayoutManager(this)
        b.recycler.adapter = adapter

        // Search
        b.edtSearch.addTextChangedListener {
            currentQuery = it?.toString().orEmpty()
            applyFilterAndSort(adapter)
        }

        // Sort menu
        b.topAppBar.inflateMenu(R.menu.main_menu)
        b.topAppBar.setOnMenuItemClickListener {
            currentSort = when (it.itemId) {
                R.id.action_sort_order -> SortMode.BY_ORDER
                R.id.action_sort_priority_high -> SortMode.PRIORITY_HIGH_FIRST
                R.id.action_sort_due_soon -> SortMode.DUE_SOONEST_FIRST
                R.id.action_sort_due_latest -> SortMode.DUE_LATEST_FIRST
                else -> return@setOnMenuItemClickListener false
            }
            applyFilterAndSort(adapter)
            true
        }

        // Drag & swipe
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                vm.move(vh.bindingAdapterPosition, target.bindingAdapterPosition)
                return true
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                vm.deleteAt(vh.bindingAdapterPosition)
            }

            override fun isLongPressDragEnabled() = true
            override fun isItemViewSwipeEnabled() = true
        })
        helper.attachToRecyclerView(b.recycler)

        lifecycleScope.launch {
            vm.tasks.collectLatest {
                allTasks = it
                applyFilterAndSort(adapter)
            }
        }

        b.fabAdd.setOnClickListener { showAddDialog() }
    }

    private fun applyFilterAndSort(adapter: TaskAdapter) {
        var list = if (currentQuery.isBlank()) allTasks
        else allTasks.filter { it.title.lowercase().contains(currentQuery.lowercase()) }

        list = when (currentSort) {
            SortMode.BY_ORDER -> list.sortedBy { it.order }
            SortMode.PRIORITY_HIGH_FIRST -> list.sortedBy { it.priority.ordinal }
            SortMode.DUE_SOONEST_FIRST -> list.sortedWith(compareBy<Task> { it.dueDate == null }.thenBy { it.dueDate ?: Long.MAX_VALUE })
            SortMode.DUE_LATEST_FIRST -> list.sortedWith(compareBy<Task> { it.dueDate == null }.thenByDescending { it.dueDate ?: Long.MIN_VALUE })
        }

        adapter.submitList(list)
    }

    private fun showAddDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(LayoutInflater.from(this))
        dialogBinding.spnPriority.adapter = ArrayAdapter.createFromResource(
            this, R.array.priorities, android.R.layout.simple_spinner_dropdown_item
        )

        val calendar = Calendar.getInstance()
        var selectedDueDate: Long? = null
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        dialogBinding.txtDueDate.setOnClickListener {
            pickDateTime(calendar) { timeInMillis ->
                selectedDueDate = timeInMillis
                dialogBinding.txtDueDate.text = "Due: ${formatter.format(calendar.time)}"
            }
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.new_task))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.add_task)) { _, _ ->
                val title = dialogBinding.edtTitle.text?.toString()?.trim().orEmpty()
                if (title.isNotEmpty()) {
                    val prio = when (dialogBinding.spnPriority.selectedItem.toString()) {
                        "HIGH" -> Priority.HIGH
                        "LOW" -> Priority.LOW
                        else -> Priority.MEDIUM
                    }
                    vm.addTask(title, prio, selectedDueDate)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showEditTaskDialog(task: Task) {
        val dialogBinding = DialogAddTaskBinding.inflate(LayoutInflater.from(this))
        dialogBinding.edtTitle.setText(task.title)
        dialogBinding.spnPriority.adapter = ArrayAdapter.createFromResource(
            this, R.array.priorities, android.R.layout.simple_spinner_item
        )
        dialogBinding.spnPriority.setSelection(
            when (task.priority) {
                Priority.HIGH -> 0
                Priority.MEDIUM -> 1
                Priority.LOW -> 2
            }
        )

        val calendar = Calendar.getInstance()
        task.dueDate?.let { calendar.timeInMillis = it }
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        dialogBinding.txtDueDate.text = task.dueDate?.let { "Due: ${formatter.format(calendar.time)}" } ?: "No due date"

        dialogBinding.txtDueDate.setOnClickListener {
            pickDateTime(calendar) { timeInMillis ->
                task.dueDate = timeInMillis
                dialogBinding.txtDueDate.text = "Due: ${formatter.format(calendar.time)}"
            }
        }

        AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = dialogBinding.edtTitle.text.toString().trim()
                if (newTitle.isNotEmpty()) {
                    val updatedTask = task.copy(
                        title = newTitle,
                        priority = when (dialogBinding.spnPriority.selectedItemPosition) {
                            0 -> Priority.HIGH
                            1 -> Priority.MEDIUM
                            else -> Priority.LOW
                        },
                        dueDate = calendar.timeInMillis
                    )
                    vm.updateTask(updatedTask)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun pickDateTime(calendar: Calendar, onPicked: (Long) -> Unit) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, y, m, d ->
            calendar.set(Calendar.YEAR, y)
            calendar.set(Calendar.MONTH, m)
            calendar.set(Calendar.DAY_OF_MONTH, d)

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            TimePickerDialog(this, { _, h, min ->
                calendar.set(Calendar.HOUR_OF_DAY, h)
                calendar.set(Calendar.MINUTE, min)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                onPicked(calendar.timeInMillis)
            }, hour, minute, true).show()
        }, year, month, day).show()
    }
}
