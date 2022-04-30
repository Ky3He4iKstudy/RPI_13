package dev.ky3he4ik.pr5_4

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ReceivingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiving)

        if (intent?.action == Intent.ACTION_SEND)
            findViewById<EditText>(R.id.editText).setText(intent.getStringExtra(Intent.EXTRA_TEXT))
    }
}