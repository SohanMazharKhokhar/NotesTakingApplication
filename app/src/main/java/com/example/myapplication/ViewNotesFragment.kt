package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewNotesFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_view_notes, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewNotes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter safely passes note ID to NoteDetailActivity
        adapter = NotesAdapter { note ->
            if (note.id > 0) {
                val intent = Intent(requireContext(), NoteDetailActivity::class.java)
                intent.putExtra("note_id", note.id)
                startActivity(intent)
            } else {
                // Debug fallback (avoids crash if note.id is invalid)
                println("⚠Invalid note id: ${note.id}")
            }
        }

        recyclerView.adapter = adapter

        // Share ViewModel across activity & fragments
        viewModel = ViewModelProvider(requireActivity())[NoteViewModel::class.java]

        // Observe LiveData safely
        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }

        return view
    }
}
