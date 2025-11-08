package com.example.roompractice


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.roompractice.data.Student
import com.example.roompractice.data.StudentDatabase
import com.example.roompractice.data.StudentDao
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var addBtn: Button
    private lateinit var dao: StudentDao
    private var studentList = listOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = StudentDatabase.getDatabase(this).studentDao()

        listView = findViewById(R.id.studentListView)
        addBtn = findViewById(R.id.addBtn)

        loadStudents()

        addBtn.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val student = studentList[position]
            val intent = Intent(this, UpdateStudentActivity::class.java)
            intent.putExtra("id", student.studentNumber)
            intent.putExtra("name", student.studentName)
            intent.putExtra("course", student.studentCourse)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudents()
    }

    private fun loadStudents() {
        CoroutineScope(Dispatchers.IO).launch {
            studentList = dao.getAllStudents()
            val displayList = studentList.map {
                "${it.studentNumber}. ${it.studentName} (${it.studentCourse})"
            }
            withContext(Dispatchers.Main) {
                listView.adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    displayList
                )
            }
        }
    }
}
