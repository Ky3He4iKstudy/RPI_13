package dev.ky3he4ik.pr4_6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TickReceiver: BroadcastReceiver() {
    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int> get() = _counter

    override fun onReceive(context: Context, intent: Intent) {
        _counter.value = (counter.value ?: 0) + 1
    }
}
