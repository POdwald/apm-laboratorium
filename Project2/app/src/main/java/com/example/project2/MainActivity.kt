package com.example.project2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun sortNumbers(view: View) {
        val stringArrayText: EditText = findViewById(R.id.stringArray)
        val sortDescriptionText: TextView = findViewById(R.id.textView_sort_description)
        val radioButtonBubbleSort: RadioButton = findViewById(R.id.radioButton_bubblesort)
        val radioButtonQuickSort: RadioButton = findViewById(R.id.radioButton_quicksort)

        val inputString = stringArrayText.text.toString()
        val numbersAsStringArray = inputString.split(",").map { it.trim() }
        var numbersArray = numbersAsStringArray.mapNotNull { it.toIntOrNull() }.toIntArray()
        if (numbersArray.isEmpty()) {
            return
        }
        if (radioButtonBubbleSort.isChecked) {
            bubbleSort(numbersArray)
            sortDescriptionText.text = getString(R.string.bubblesort_description)
        } else if (radioButtonQuickSort.isChecked) {
            quickSort(numbersArray)
            sortDescriptionText.text = getString(R.string.quicksort_description)
        }

        val outputString = numbersArray.joinToString(",")
        val intent = Intent(
            this@MainActivity,
            MainActivity2::class.java
        )
        intent.putExtra("outputString", outputString)
        startActivity(intent)
    }

    private fun bubbleSort(array: IntArray) {
        val n = array.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (array[j] > array[j + 1]) {
                    val temp = array[j]
                    array[j] = array[j + 1]
                    array[j + 1] = temp
                }
            }
        }
    }

    private fun quickSort(array: IntArray, low: Int = 0, high: Int = array.size - 1) {
        if (low < high) {
            val pivotIndex = partition(array, low, high)
            quickSort(array, low, pivotIndex - 1)
            quickSort(array, pivotIndex + 1, high)
        }
    }

    private fun partition(array: IntArray, low: Int, high: Int): Int {
        val pivot = array[high]
        var i = low - 1
        for (j in low until high) {
            if (array[j] <= pivot) {
                i++
                val temp = array[i]
                array[i] = array[j]
                array[j] = temp
            }
        }
        val temp = array[i + 1]
        array[i + 1] = array[high]
        array[high] = temp
        return i + 1
    }
}