package com.example.project2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), InputFragment.OnSortCompleteListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState == null) {
            if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, InputFragment())
                    .commit()
            }
        }
    }
    override fun onSortComplete(outputString: String, outputDescription: String) {
        Log.d("MainActivity", "Sort complete: outputString = $outputString, outputDescription = $outputDescription")
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                OutputFragment.newInstance(outputString, outputDescription)
            )
            .addToBackStack(null)
            .commit()
    }
}
