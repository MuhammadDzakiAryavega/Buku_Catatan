package com.dzaki_aryavega.buku_catatan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dzaki_aryavega.buku_catatan.databinding.ActivityAddBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var db: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0, title, content)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }
    }
}