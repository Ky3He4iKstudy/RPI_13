package dev.ky3he4ik.pr4_9

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class TextWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.d("test_worker", "text_worker_start")
        val c = doWork(0, CharArray(6))
        Log.d("test_worker", "text_worker_stop")
        c ?: return Result.failure()
        val d = Data.Builder()
            .putAll(inputData)
            .putString("data_is", c)
            .build()
        return Result.success(d)
    }

    private fun doWork(depth: Int, word: CharArray): String? {
        if (depth == 6)
            return if (word.concatToString() == "friend") "friend" else null
        for (i in 'a'..'z') {
            word[depth] = i
            val r = doWork(depth + 1, word)
            if (r != null)
                return r
        }
        return null
    }
}
