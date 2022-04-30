package study.android.room2

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch
import study.android.room2.entities.Student
import study.android.room2.entities.Subject

class MainActivity : AppCompatActivity() {
    private lateinit var rbStudent: RadioButton
    private lateinit var rbSubject: RadioButton
    private lateinit var spinner: Spinner
    private lateinit var listCaption: TextView
    private lateinit var recyclerView: RecyclerView
    private var students: List<String> = listOf()
    private var subjects: List<String> = listOf()
    private var isStudents = true

    private val db by lazy {
        Room.databaseBuilder(
            this,
            SchoolDatabase::class.java, "school.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rbStudent = findViewById(R.id.rbStudent)
        rbSubject = findViewById(R.id.rbSubject)
        spinner = findViewById(R.id.spinner)
        listCaption = findViewById(R.id.listCaption)
        recyclerView = findViewById(R.id.recyclerView)

        rbStudent.setOnClickListener {
            listCaption.text = "Student's subjects"
            lifecycleScope.launch {
                students = db.schoolDao.getAllStudentNames()
                spinner.adapter =
                    ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, students)
                isStudents = true
            }
        }

        rbSubject.setOnClickListener {
            listCaption.text = "Students study"
            lifecycleScope.launch {
                subjects = db.schoolDao.getAllSubjectNames()
                spinner.adapter =
                    ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, subjects)
                isStudents = false
            }
        }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?,
                position: Int, id: Long
            ) {
                lifecycleScope.launch {
                    val result = if (isStudents) {
                        db.schoolDao.getSubjectsOfStudent(students[position]).map {
                            it.subjects.map(Subject::subjectName)
                        }.flatten()
                    } else
                        db.schoolDao.getStudentsOfSubject(subjects[position]).map {
                            it.students.map(Student::studentName)
                        }.flatten()
                    recyclerView.adapter = MyAdapter(result)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        db.schoolDao.apply {
            lifecycleScope.launch {
                DataExample.directors.forEach { insertDirector(it) }
                DataExample.schools.forEach { insertSchool(it) }
                DataExample.subjects.forEach { insertSubject(it) }
                DataExample.students.forEach { insertStudent(it) }
                DataExample.studentSubjectRelations.forEach { insertStudentSubjectCrossRef(it) }
            }
        }
    }
}
