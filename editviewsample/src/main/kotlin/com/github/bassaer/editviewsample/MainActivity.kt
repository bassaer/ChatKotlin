package com.github.bassaer.editviewsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val displayText: TextView = findViewById(R.id.display) as TextView
        val editText: EditText = findViewById(R.id.edit_text) as EditText
        val sendButton: Button = findViewById(R.id.send_button) as Button

        sendButton.setOnClickListener {
            if (!editText.text.isBlank()) {
                val builder: StringBuilder = StringBuilder()
                builder.append(displayText.text)
                builder.append(getString(R.string.br))
                builder.append(editText.text)
                displayText.text = builder.toString()
                editText.text.clear()
            }
        }

    }
}
