package com.example.tasklytic

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasklytic.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var taskAdapter: TaskAdapter
    private val tasksList = mutableListOf<Pair<String, String>>()  // List to hold task data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("Note_DATA", Context.MODE_PRIVATE)

        // Initialize RecyclerView
        taskAdapter = TaskAdapter(tasksList, ::editTask, ::deleteTask)
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.adapter = taskAdapter

        // Load tasks when MainActivity2 starts
        loadTasks()

        // Set onClickListener for imageView6 to navigate to MainActivity3
        binding.imageView6.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        // Set onClickListener for imageView4 to navigate to TimerActivity
        binding.imageView4.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }

        // Set onClickListener for imageView7 to navigate to StopwatchActivity
        binding.imageView7.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }
    }

    // Load all saved tasks and display with RecyclerView
    private fun loadTasks() {
        tasksList.clear()  // Clear previous tasks
        val allTasks = sharedPreferences.all
        if (allTasks.isNotEmpty()) {
            for ((key, value) in allTasks) {
                tasksList.add(Pair(key, value.toString()))
            }
            taskAdapter.notifyDataSetChanged()  // Notify adapter of data changes
        } else {
            Toast.makeText(this, "No tasks found", Toast.LENGTH_SHORT).show()
        }
    }

    // Edit a specific task
    private fun editTask(taskKey: String) {
        val intent = Intent(this, MainActivity3::class.java)
        intent.putExtra("task_key", taskKey)
        startActivity(intent)
    }

    // Delete a specific task
    private fun deleteTask(taskKey: String) {
        val editor = sharedPreferences.edit()
        editor.remove(taskKey)
        editor.apply()

        Toast.makeText(this, "Task Deleted", Toast.LENGTH_SHORT).show()

        // Reload tasks after deletion
        loadTasks()
    }
}
