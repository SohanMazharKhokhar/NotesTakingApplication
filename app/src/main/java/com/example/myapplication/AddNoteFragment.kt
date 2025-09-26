package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class AddNoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)

        viewModel = ViewModelProvider(requireActivity())[NoteViewModel::class.java]

        val titleEt = view.findViewById<EditText>(R.id.etTitle)
        val descEt = view.findViewById<EditText>(R.id.etDescription)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val title = titleEt.text.toString().trim()
            val desc = descEt.text.toString().trim()

            if (title.isEmpty() || desc.isEmpty()) {
                Toast.makeText(requireContext(), "Both fields required", Toast.LENGTH_SHORT).show()
            } else {
                val note = Note(title = title, description = desc)
                viewModel.insert(note)
                Toast.makeText(requireContext(), "Note Saved", Toast.LENGTH_SHORT).show()
                titleEt.text.clear()
                descEt.text.clear()
            }
        }

        return view
    }
}
