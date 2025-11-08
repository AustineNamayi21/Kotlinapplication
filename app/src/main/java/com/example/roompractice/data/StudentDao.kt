package com.example.roompractice.data


import androidx.room.*

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM students ORDER BY studentName ASC")
    suspend fun getAllStudents(): List<Student>

    @Query("SELECT * FROM students WHERE studentNumber = :studentNumber")
    suspend fun getStudentByNumber(studentNumber: String): Student?

    @Query("DELETE FROM students WHERE studentNumber = :studentNumber")
    suspend fun deleteStudentByNumber(studentNumber: String)  // Make sure this exists!
}