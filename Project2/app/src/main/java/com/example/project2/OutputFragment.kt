package com.example.project2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private const val ARG_SORTED_ARRAY = "sorted_array"
private const val ARG_SORT_DESCRIPTION = "sort_description"

class OutputFragment : Fragment() {
    private var sortedArray: String? = null
    private var sortDescription: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sortedArray = it.getString(ARG_SORTED_ARRAY)
            sortDescription = it.getString(ARG_SORT_DESCRIPTION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_output, container, false)

        val sortedArrayTextView: TextView = rootView.findViewById(R.id.textView_output_values)
        val sortDescriptionTextView: TextView = rootView.findViewById(R.id.textView_sort_description)
        val backButton: Button = rootView.findViewById(R.id.button_back)

        sortedArray?.let { array ->
            sortedArrayTextView.text = array
        }

        sortDescription?.let { description ->
            sortDescriptionTextView.text = description
        }

        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(sortedArray: String, sortDescription: String) =
            OutputFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SORTED_ARRAY, sortedArray)
                    putString(ARG_SORT_DESCRIPTION, sortDescription)
                }
            }
    }
}
