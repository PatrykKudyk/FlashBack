package com.partos.flashback.recycler

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.R
import com.partos.flashback.models.MyPackage
import kotlinx.android.synthetic.main.row_package.view.*

class PackageRecyclerViewAdapter(var packagesList: ArrayList<MyPackage>) :
    RecyclerView.Adapter<PackageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.row_package, parent, false)
        return PackageViewHolder(cellRow)
    }

    override fun getItemCount(): Int {
        return packagesList.size
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        var editing = false
        val title = holder.view.package_cell_name
        val editButton = holder.view.package_cell_image_view_edit
        val saveButton = holder.view.package_cell_image_view_save
        val deleteButton = holder.view.package_cell_image_view_delete
        val deleteYes = holder.view.package_cell_button_delete_yes
        val deleteNo = holder.view.package_cell_button_delete_no
        val titleEdit = holder.view.package_cell_name_edit_text
        val cardView = holder.view.package_cell_card_view

        title.text = packagesList[position].title
        editButton.setOnClickListener {
            holder.view.package_cell_name_edit_text_layout.visibility = View.VISIBLE
            holder.view.package_cell_linear_layout_edit.visibility = View.VISIBLE
            editButton.visibility = View.GONE
            title.visibility = View.GONE
            titleEdit.setText(title.text.toString())
        }
        saveButton.setOnClickListener {
            if (titleEdit.text.toString() != "") {
                packagesList[position].title = titleEdit.text.toString()
                title.setText(titleEdit.text.toString())
                holder.view.package_cell_name_edit_text_layout.visibility = View.GONE
                holder.view.package_cell_linear_layout_edit.visibility = View.GONE
                editButton.visibility = View.VISIBLE
                title.visibility = View.VISIBLE
            } else {
                Toast.makeText(
                    holder.view.context,
                    holder.view.context.getString(R.string.toast_package_name_not_null),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        deleteButton.setOnClickListener {
            holder.view.package_cell_linear_layout_main.visibility = View.GONE
            holder.view.package_cell_linear_layout_delete.visibility = View.VISIBLE
        }

        deleteYes.setOnClickListener {
            packagesList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, packagesList.size)
        }

        deleteNo.setOnClickListener {
            holder.view.package_cell_linear_layout_main.visibility = View.VISIBLE
            holder.view.package_cell_linear_layout_delete.visibility = View.GONE
        }

        cardView.setOnClickListener {
//            val flashcardsFragment = FlashcardsFragment.newInstance(packagesList[position].id)
//            val manager = (holder.itemView.context as MainActivity).supportFragmentManager
//            manager
//                .beginTransaction()
//                .setCustomAnimations(
//                    R.anim.enter_left_to_right, R.anim.exit_right_to_left,
//                    R.anim.enter_right_to_left, R.anim.exit_left_to_right
//                )
//                .replace(R.id.main_frame_layout, flashcardsFragment)
//                .addToBackStack(FlashcardsFragment.toString())
//                .commit()
        }
    }

}


class PackageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}