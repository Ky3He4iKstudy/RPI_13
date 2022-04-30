package dev.ky3he4ik.pr4_6

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BatteryReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "накормите Ждуна, силы на исходе!", Toast.LENGTH_SHORT).show()
    }
}