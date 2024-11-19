package com.dzaki_aryavega.buku_catatan.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dzaki_aryavega.buku_catatan.Note
import com.dzaki_aryavega.buku_catatan.NoteDatabaseHelper
import com.dzaki_aryavega.buku_catatan.R
import com.dzaki_aryavega.buku_catatan.UpdateNoteActivity

class NotesAdapter(private var notes: List<Note>, context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Confirmation")
                setMessage("Do you want to continue?")
                setIcon(R.drawable.baseline_delete_24)

                setPositiveButton("Yes"){dialogInterface, i ->
                    db.deleteNote(note.id)
                    refreshData(db.getAllNotes())
                    Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }

                setNegativeButton("Cancel"){dialogInterface, i->
                    dialogInterface.dismiss()
                }
            }.show()
        }
    }

    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}