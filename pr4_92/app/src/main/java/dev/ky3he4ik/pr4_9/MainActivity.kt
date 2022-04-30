package dev.ky3he4ik.pr4_9

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*

class MainActivity : AppCompatActivity() {
    private lateinit var data: Data
    private lateinit var process1: OneTimeWorkRequest
    private lateinit var process2: OneTimeWorkRequest

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.clicker).apply {
            setOnClickListener {
                count += 1
                text = count.toString()
            }
        }
        findViewById<Button>(R.id.start_process).setOnClickListener {
            data = workDataOf("click" to count)
            process1 = OneTimeWorkRequestBuilder<TextWorker>()
                .addTag("process1")
                .build()
            process2 = OneTimeWorkRequestBuilder<LongWorker>()
                .setInputData(data)
                .addTag("process2")
                .build()

            WorkManager.getInstance(this)
                .beginWith(process1)
                .then(process2)
                .enqueue()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WorkManager.getInstance(this).apply {
            cancelAllWorkByTag("process1")
            cancelAllWorkByTag("process2")
        }
    }
}
