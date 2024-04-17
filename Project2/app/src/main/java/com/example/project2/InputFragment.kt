package com.example.project2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class InputFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_input, container, false)
        val buttonSort: Button = rootView.findViewById(R.id.button_sort)

        buttonSort.setOnClickListener {
            val (outputString, outputDescription) = sortNumbers()
            (activity as? OnSortCompleteListener)?.onSortComplete(outputString, outputDescription)
        }

        return rootView
    }
    private fun sortNumbers(): Pair<String, String> {
        val rootView = requireView()
        val stringArrayText: EditText = rootView.findViewById(R.id.stringArray)
        val radioButtonBubbleSort: RadioButton = rootView.findViewById(R.id.radioButton_bubblesort)
        val radioButtonQuickSort: RadioButton = rootView.findViewById(R.id.radioButton_quicksort)

        val inputString = stringArrayText.text.toString()
        val numbersAsStringArray = inputString.split(",").map { it.trim() }
        val numbersArray = numbersAsStringArray.mapNotNull { it.toIntOrNull() }.toIntArray()

        if (numbersArray.isEmpty()) {
            return "" to "Array is empty"
        }

        val outputDescription: String = if (radioButtonBubbleSort.isChecked) {
            bubbleSort(numbersArray)
            getString(R.string.bubblesort_description)
        } else if (radioButtonQuickSort.isChecked) {
            quickSort(numbersArray)
            getString(R.string.quicksort_description)
        } else {
            "No sorting algorithm selected"
        }

        val outputString = numbersArray.joinToString(",")
        return outputString to outputDescription
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

    interface OnSortCompleteListener {
        fun onSortComplete(outputString: String, outputDescription: String)
    }
}