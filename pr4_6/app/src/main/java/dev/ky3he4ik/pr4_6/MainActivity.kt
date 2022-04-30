package dev.ky3he4ik.pr4_6

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val tickReceiver = TickReceiver()
    private val batteryReceiver = BatteryReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            unregisterReceiver(tickReceiver)
            Toast.makeText(this, R.string.toast_text, Toast.LENGTH_SHORT).show()
        }

        registerReceiver(tickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_LOW))

        tickReceiver.counter.observe(this) {
            findViewById<TextView>(R.id.print).text = "время созерцания: $it мин."
        }
    }
}
