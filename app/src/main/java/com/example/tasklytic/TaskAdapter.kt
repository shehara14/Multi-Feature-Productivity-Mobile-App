package com.example.tasklytic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklytic.databinding.ItemTaskBinding

class TaskAdapter(
    private val tasks: MutableList<Pair<String, String>>,  // Mutable list to allow updates
    private val onEditClick: (String) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(taskKey: String, taskValue: String) {
            binding.taskTextView.text = taskValue
            binding.editButton.setOnClickListener { onEditClick(taskKey) }
            binding.deleteButton.setOnClickListener { onDeleteClick(taskKey) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val (key, value) = tasks[position]
        holder.bind(key, value)
    }

    override fun getItemCount(): Int = tasks.size
}
