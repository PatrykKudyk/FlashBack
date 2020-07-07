package com.partos.flashback.recycler.packages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.MainActivity
import com.partos.flashback.R
import com.partos.flashback.db.DataBaseHelper
import com.partos.flashback.models.MyFlashcard
import com.partos.flashback.models.MyPackage
import kotlinx.android.synthetic.main.row_assign_package.view.*

class AssignPackageRecyclerViewAdapter(
    var packagesList: ArrayList<MyPackage>,
    val flashcard: MyFlashcard
) :
    RecyclerView.Adapter<AssignPackageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignPackageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.row_assign_package, parent, false)
        return AssignPackageViewHolder(
            cellRow
        )
    }

    override fun getItemCount(): Int {
        return packagesList.size
    }

    override fun onBindViewHolder(holder: AssignPackageViewHolder, position: Int) {
        holder.view.assign_package_cell_name.setText(packagesList[position].title)
        holder.view.assign_package_cell_card_view.setOnClickListener {
            val db = DataBaseHelper(holder.view.context)
            if (db.updateFlashcard(
                    MyFlashcard(
                        flashcard.id,
                        flashcard.userId,
                        packagesList[position].id,
                        flashcard.polish,
                        flashcard.english,
                        flashcard.knowledgeLevel,
                        flashcard.isNew,
                        flashcard.isKnown
                    )
                )
            ) {
                Toast.makeText(
                    holder.view.context,
                    R.string.toast_flashcard_changed,
                    Toast.LENGTH_SHORT
                ).show()
                val manager = (holder.itemView.context as MainActivity).supportFragmentManager
                manager
                    .popBackStack()
            } else {
                Toast.makeText(
                    holder.view.context,
                    R.string.toast_flashcard_not_changed,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}


class AssignPackageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}