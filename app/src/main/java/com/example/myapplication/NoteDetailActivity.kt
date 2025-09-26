package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        // Setup toolbar with back button
        val toolbar: Toolbar? = findViewById(R.id.toolbarNoteDetail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titleTv = findViewById<TextView>(R.id.tvTitleDetail)
        val descTv = findViewById<TextView>(R.id.tvDescriptionDetail)

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        //Safely get noteId
        val noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            titleTv.text = "Error"
            descTv.text = "Note ID not found."
            return
        }

        //Fetch note in background
        lifecycleScope.launch {
            val note = viewModel.getNoteById(noteId)
            if (note != null) {
                titleTv.text = note.title
                descTv.text = note.description

                //Show note title in toolbar
                supportActionBar?.title = note.title
            } else {
                titleTv.text = "Note not found"
                descTv.text = "This note may have been deleted."
                supportActionBar?.title = "Note Missing"
            }
        }
    }

    // Handle back navigation
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
