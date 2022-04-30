package dev.ky3he4ik.pr4_9

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class LongWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        Log.d("test_worker", "long_worker_start")

        val data1 = inputData.getString("data_is") ?: "123456"
        val data2 = inputData.getInt("click", 100)

        val p = (1L..data1.length * 10000).reduce { acc, i ->
            acc + (1L..data2).reduce { acc2, j -> acc2 + i % j }
        }

        Log.d("test_worker", "long_worker_stop with rezult  $p")
        return Result.success()
    }
}