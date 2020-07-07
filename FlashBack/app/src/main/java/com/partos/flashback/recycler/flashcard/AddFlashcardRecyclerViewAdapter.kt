package com.partos.flashback.recycler.flashcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.MainActivity
import com.partos.flashback.R
import com.partos.flashback.db.DataBaseHelper
import com.partos.flashback.recycler.packages.AddPackageViewHolder
import kotlinx.android.synthetic.main.row_add_flashcard.view.*
import kotlinx.android.synthetic.main.row_add_package.view.*

class AddFlashcardRecyclerViewAdapter(val userdId: Long, val packageId: Long) :
    RecyclerView.Adapter<AddFlashcardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFlashcardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cell = layoutInflater.inflate(R.layout.row_add_flashcard, parent, false)
        return AddFlashcardViewHolder(cell)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: AddFlashcardViewHolder, position: Int) {
        holder.view.add_flashcard_button_add.setOnClickListener {
            if (holder.view.add_flashcard_english_edit_text.text.toString() != "" &&
                holder.view.add_flashcard_polish_edit_text.text.toString() != ""
            ) {
                val db = DataBaseHelper(holder.view.context)
                if (db.addFlashcard(
                        userdId,
                        packageId,
                        holder.view.add_flashcard_polish_edit_text.text.toString(),
                        holder.view.add_flashcard_english_edit_text.text.toString(),
                        0,
                        0,
                        0
                    )
                ) {
                    Toast.makeText(
                        holder.view.context,
                        holder.view.context.getString(R.string.toast_flashcard_added),
                        Toast.LENGTH_SHORT
                    ).show()
                    val manager = (holder.itemView.context as MainActivity).supportFragmentManager
                    manager
                        .popBackStack(
                            manager!!.getBackStackEntryAt((manager!!.backStackEntryCount - 2)).id,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                } else {
                    Toast.makeText(
                        holder.view.context,
                        holder.view.context.getString(R.string.toast_flashcard_not_added),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    holder.view.context,
                    holder.view.context.getString(R.string.toast_flashcard_not_null),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

}

class AddFlashcardViewHolder(val view: View) : RecyclerView.ViewHolder(view)