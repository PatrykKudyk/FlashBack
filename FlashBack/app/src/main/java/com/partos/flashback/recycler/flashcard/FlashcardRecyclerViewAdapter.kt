package com.partos.flashback.recycler.flashcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.R
import com.partos.flashback.db.DataBaseHelper
import com.partos.flashback.models.MyFlashcard
import kotlinx.android.synthetic.main.row_flashcard.view.*

class FlashcardRecyclerViewAdapter(var flashcardList: ArrayList<MyFlashcard>) :
    RecyclerView.Adapter<FlashcardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowCell = layoutInflater.inflate(R.layout.row_flashcard, parent, false)
        return FlashcardViewHolder(
            rowCell
        )
    }

    override fun getItemCount(): Int {
        return flashcardList.size
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        holder.view.flashcard_cell_text_view_english.setText(flashcardList[position].english)
        holder.view.flashcard_cell_text_view_polish.setText(flashcardList[position].polish)
        holder.view.flashcard_cell_image_view_delete.setOnClickListener {
            holder.view.flashcard_cell_constraint_main.visibility = View.GONE
            holder.view.flashcard_cell_constraint_delete.visibility = View.VISIBLE
        }
        holder.view.flashcard_cell_button_delete_no.setOnClickListener {
            holder.view.flashcard_cell_constraint_main.visibility = View.VISIBLE
            holder.view.flashcard_cell_constraint_delete.visibility = View.GONE
        }
        holder.view.flashcard_cell_button_delete_yes.setOnClickListener {
            val db = DataBaseHelper(holder.view.context)
            db.deleteFlashcard(flashcardList[position].id)
            flashcardList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(0, flashcardList.size)
            holder.view.flashcard_cell_constraint_main.visibility = View.VISIBLE
            holder.view.flashcard_cell_constraint_delete.visibility = View.GONE
        }
        holder.view.flashcard_cell_image_view_edit.setOnClickListener {
            holder.view.flashcard_cell_constraint_main.visibility = View.GONE
            holder.view.flashcard_cell_constraint_edit.visibility = View.VISIBLE
            holder.view.flashcard_cell_edit_text_view_polish.setText(holder.view.flashcard_cell_text_view_polish.text)
            holder.view.flashcard_cell_edit_text_view_english.setText(holder.view.flashcard_cell_text_view_english.text)
        }
        holder.view.flashcard_cell_image_view_save.setOnClickListener {
            flashcardList[position].english =
                holder.view.flashcard_cell_edit_text_view_english.text.toString()
            flashcardList[position].polish =
                holder.view.flashcard_cell_edit_text_view_polish.text.toString()
            holder.view.flashcard_cell_text_view_english.setText(holder.view.flashcard_cell_edit_text_view_english.text)
            holder.view.flashcard_cell_text_view_polish.setText(holder.view.flashcard_cell_edit_text_view_polish.text)
            holder.view.flashcard_cell_constraint_main.visibility = View.VISIBLE
            holder.view.flashcard_cell_constraint_edit.visibility = View.GONE
        }
    }

}


class FlashcardViewHolder(val view: View) : RecyclerView.ViewHolder(view)