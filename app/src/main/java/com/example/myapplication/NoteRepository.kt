package com.example.myapplication

import androidx.lifecycle.LiveData

class NoteRepository(private val dao: NoteDao) {
    val allNotes: LiveData<List<Note>> = dao.getAllNotes()

    suspend fun insert(note: Note) {
        dao.insert(note)
    }

    suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }
}
