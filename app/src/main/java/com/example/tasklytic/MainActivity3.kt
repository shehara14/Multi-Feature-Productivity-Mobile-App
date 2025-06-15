package com.example.tasklytic

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.tasklytic.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private lateinit var sharedPreferences: SharedPreferences
    private var isEditMode = false
    private var taskKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Note_DATA", Context.MODE_PRIVATE)

        // Check if we're editing a task
        taskKey = intent.getStringExtra("task_key")
        isEditMode = !taskKey.isNullOrEmpty()

        if (isEditMode) {
            val taskToEdit = sharedPreferences.getString(taskKey, "")
            binding.editTextText.setText(taskToEdit)
            binding.button.text = "Update Task"
        } else {
            binding.button.text = "Save Task"
        }

        // Save or Update Task
        binding.button.setOnClickListener {
            val note = binding.editTextText.text.toString()

            if (note.isNotEmpty()) {
                val editor = sharedPreferences.edit()

                if (isEditMode) {
                    editor.putString(taskKey, note)
                    Toast.makeText(this, "Task Updated Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    val taskId = System.currentTimeMillis().toString()
                    editor.putString(taskId, note)
                    Toast.makeText(this, "Task Saved Successfully", Toast.LENGTH_SHORT).show()
                }

                editor.apply()
                binding.editTextText.text.clear()

                // After saving or editing, go back to MainActivity2
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish()  // Close MainActivity3
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
