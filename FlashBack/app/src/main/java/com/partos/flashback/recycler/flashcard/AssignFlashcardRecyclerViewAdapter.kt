package com.partos.flashback.recycler.flashcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.MainActivity
import com.partos.flashback.R
import com.partos.flashback.fragments.packages.AssignPackagesFragment
import com.partos.flashback.models.MyFlashcard
import kotlinx.android.synthetic.main.row_assign_flashcard.view.*

class AssignFlashcardRecyclerViewAdapter(var flashcardList: ArrayList<MyFlashcard>) :
    RecyclerView.Adapter<AssignFlashcardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignFlashcardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rowCell = layoutInflater.inflate(R.layout.row_assign_flashcard, parent, false)
        return AssignFlashcardViewHolder(
            rowCell
        )
    }

    override fun getItemCount(): Int {
        return flashcardList.size
    }

    override fun onBindViewHolder(holder: AssignFlashcardViewHolder, position: Int) {
        holder.view.assign_flashcard_cell_text_view_english.setText(flashcardList[position].english)
        holder.view.assign_flashcard_cell_text_view_polish.setText(flashcardList[position].polish)
        holder.view.assign_flashcard_cell_card_view.setOnClickListener {
            val assignPackagesFragment = AssignPackagesFragment.newInstance()
            val manager = (holder.itemView.context as MainActivity).supportFragmentManager
            manager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_left_to_right, R.anim.exit_right_to_left,
                    R.anim.enter_right_to_left, R.anim.exit_left_to_right
                )
                .replace(R.id.main_frame_layout, assignPackagesFragment)
                .addToBackStack(AssignPackagesFragment.toString())
                .commit()
        }
    }

}


class AssignFlashcardViewHolder(val view: View) : RecyclerView.ViewHolder(view)