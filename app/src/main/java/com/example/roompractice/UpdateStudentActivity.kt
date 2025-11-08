package com.example.roompractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.roompractice.data.Student
import com.example.roompractice.data.StudentDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateStudentActivity : AppCompatActivity() {

    private lateinit var etStudentNumber: EditText
    private lateinit var etStudentName: EditText
    private lateinit var etStudentCourse: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    private var originalStudentNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)

        // Initialize views - make sure these IDs match your XML
        etStudentNumber = findViewById(R.id.etStudentNumber)
        etStudentName = findViewById(R.id.etStudentName)
        etStudentCourse = findViewById(R.id.etStudentCourse)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        // Get data from intent
        originalStudentNumber = intent.getStringExtra("id") ?: ""
        val name = intent.getStringExtra("name") ?: ""
        val course = intent.getStringExtra("course") ?: ""

        // Populate fields
        etStudentNumber.setText(originalStudentNumber)
        etStudentName.setText(name)
        etStudentCourse.setText(course)

        // Make student number non-editable for update
        etStudentNumber.isEnabled = false

        btnUpdate.setOnClickListener {
            updateStudent()
        }

        btnDelete.setOnClickListener {
            deleteStudent()
        }
    }

    private fun updateStudent() {
        val studentNumber = etStudentNumber.text.toString().trim()
        val studentName = etStudentName.text.toString().trim()
        val studentCourse = etStudentCourse.text.toString().trim()

        if (studentName.isEmpty() || studentCourse.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(studentNumber, studentName, studentCourse)

        // Same pattern as your MainActivity
        CoroutineScope(Dispatchers.IO).launch {
            try {
                StudentDatabase.getDatabase(this@UpdateStudentActivity)
                    .studentDao()
                    .updateStudent(student)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdateStudentActivity, "Student updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdateStudentActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteStudent() {
        // Same pattern as your MainActivity
        CoroutineScope(Dispatchers.IO).launch {
            try {
                StudentDatabase.getDatabase(this@UpdateStudentActivity)
                    .studentDao()
                    .deleteStudentByNumber(originalStudentNumber)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdateStudentActivity, "Student deleted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdateStudentActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}