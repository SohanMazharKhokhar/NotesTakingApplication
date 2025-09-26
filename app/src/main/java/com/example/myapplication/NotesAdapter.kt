package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private val onClick: (Note) -> Unit) :
    ListAdapter<Note, NotesAdapter.NoteViewHolder>(DiffCallback()) {

    class NoteViewHolder(itemView: View, private val onClick: (Note) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val titleView: TextView = itemView.findViewById(R.id.tvNoteTitle)
        private var currentNote: Note? = null

        init {
            itemView.setOnClickListener {
                currentNote?.let { note ->
                    onClick(note)  //Safe click handling
                }
            }
        }

        fun bind(note: Note) {
            currentNote = note
            titleView.text = note.title.ifEmpty { "Untitled Note" } // Prevents blank titles
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            // Defensive null-safe check (useful if ids are default = 0)
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
