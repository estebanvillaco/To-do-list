package com.example.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.DialogAddTaskBinding
import com.example.todo.model.Priority
import com.example.todo.ui.TaskAdapter
import com.example.todo.ui.TaskViewModel
import com.example.todo.ui.TaskViewModelFactory
import androidx.core.widget.addTextChangedListener
import com.example.todo.model.Task
import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.app.TimePickerDialog



class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    private val vm: TaskViewModel by viewModels {
        TaskViewModelFactory(application)
    }
    private var allTasks: List<Task> = emptyList()
    private var currentQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val adapter = TaskAdapter(onToggle = vm::toggleDone)
        b.recycler.layoutManager = LinearLayoutManager(this)
        b.recycler.adapter = adapter
        b.edtSearch.addTextChangedListener { text ->
            currentQuery = text?.toString().orEmpty()
            applyFilter(adapter)
        }


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
            vm.tasks.collectLatest { tasks ->
                allTasks = tasks
                applyFilter(adapter)
            }
        }

        b.fabAdd.setOnClickListener { showAddDialog() }
    }
    private fun applyFilter(adapter: TaskAdapter) {
        val filtered = if (currentQuery.isBlank()) {
            allTasks
        } else {
            val q = currentQuery.lowercase()
            allTasks.filter { it.title.lowercase().contains(q) }
        }
        adapter.submitList(filtered)
    }

    private fun showAddDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(LayoutInflater.from(this))

        dialogBinding.spnPriority.adapter = ArrayAdapter.createFromResource(
            this, R.array.priorities, android.R.layout.simple_spinner_dropdown_item
        )

        // Store selected date+time here (millis)
        val calendar = Calendar.getInstance()
        var selectedDueDate: Long? = null
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        dialogBinding.txtDueDate.setOnClickListener {
            // 1) Pick DATE first
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                calendar.set(Calendar.YEAR, y)
                calendar.set(Calendar.MONTH, m)
                calendar.set(Calendar.DAY_OF_MONTH, d)

                // 2) Then pick TIME
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                TimePickerDialog(this, { _, h, min ->
                    calendar.set(Calendar.HOUR_OF_DAY, h)
                    calendar.set(Calendar.MINUTE, min)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)

                    selectedDueDate = calendar.timeInMillis
                    dialogBinding.txtDueDate.text = "Due: ${formatter.format(calendar.time)}"
                }, hour, minute, true).show()

            }, year, month, day).show()
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.new_task))
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.add_task)) { _, _ ->
                val title = dialogBinding.edtTitle.text?.toString()?.trim().orEmpty()
                val prio = when (dialogBinding.spnPriority.selectedItem.toString()) {
                    "HIGH" -> Priority.HIGH
                    "LOW" -> Priority.LOW
                    else -> Priority.MEDIUM
                }
                if (title.isNotEmpty()) {
                    vm.addTask(title, prio, selectedDueDate)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }


}
