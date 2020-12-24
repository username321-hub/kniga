package com.arbonik.kniga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var True:Button
    lateinit var False:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        True = findViewById(R.id.True)
        False = findViewById(R.id.False)
        True.setOnClickListener {
            Toast.makeText(
                this,
                R.string.corc_toast,
                Toast.LENGTH_SHORT)
                .show()
        }
        False.setOnClickListener {
            Toast.makeText(
                this,
                R.string.incr_toast,
                Toast.LENGTH_SHORT)
                .show()
        }
    }
}