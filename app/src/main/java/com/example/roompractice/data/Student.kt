package com.example.roompractice.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    val studentNumber: String,  // Should be String
    val studentName: String,
    val studentCourse: String
)