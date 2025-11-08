package com.example.roompractice

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

class AddStudentActivity : AppCompatActivity() {

    private lateinit var etStudentNumber: EditText
    private lateinit var etStudentName: EditText
    private lateinit var etStudentCourse: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        // FIXED: Using the correct IDs from your XML
        etStudentNumber = findViewById(R.id.numberET)
        etStudentName = findViewById(R.id.nameET)
        etStudentCourse = findViewById(R.id.courseET)
        btnSave = findViewById(R.id.saveBtn)

        btnSave.setOnClickListener {
            saveStudent()
        }
    }

    private fun saveStudent() {
        val studentNumber = etStudentNumber.text.toString().trim()
        val studentName = etStudentName.text.toString().trim()
        val studentCourse = etStudentCourse.text.toString().trim()

        if (studentNumber.isEmpty() || studentName.isEmpty() || studentCourse.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(studentNumber, studentName, studentCourse)

        // Same pattern as your MainActivity
        CoroutineScope(Dispatchers.IO).launch {
            try {
                StudentDatabase.getDatabase(this@AddStudentActivity)
                    .studentDao()
                    .insertStudent(student)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddStudentActivity, "Student saved successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddStudentActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}