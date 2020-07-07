package com.partos.flashback.recycler.packages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.MainActivity
import com.partos.flashback.R
import com.partos.flashback.db.DataBaseHelper
import kotlinx.android.synthetic.main.row_add_package.view.*

class AddPackageRecyclerViewAdapter(val userId: Long) :
    RecyclerView.Adapter<AddPackageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPackageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cell = layoutInflater.inflate(R.layout.row_add_package, parent, false)
        return AddPackageViewHolder(cell)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: AddPackageViewHolder, position: Int) {
        holder.view.add_package_button_add.setOnClickListener {
            if (holder.view.add_package_name_edit_text.text.toString() != "") {
                val db = DataBaseHelper(holder.view.context)
                if (db.addPackage(holder.view.add_package_name_edit_text.text.toString(), userId)) {
                    val manager = (holder.itemView.context as MainActivity).supportFragmentManager
                    manager
                        .popBackStack()
                } else {
                    Toast.makeText(
                        holder.view.context,
                        holder.view.context.getString(R.string.toast_package_not_added),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    holder.view.context,
                    holder.view.context.getString(R.string.toast_package_name_not_null),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}


class AddPackageViewHolder(val view: View) : RecyclerView.ViewHolder(view)