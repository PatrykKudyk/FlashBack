package com.partos.flashback.recycler.packages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.MainActivity
import com.partos.flashback.R
import kotlinx.android.synthetic.main.row_add_package.view.*

class AddPackageRecyclerViewAdapter() : RecyclerView.Adapter<AddPackageViewHolder>() {
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
            val manager = (holder.itemView.context as MainActivity).supportFragmentManager
            manager
                .popBackStack()
        }
    }

}


class AddPackageViewHolder(val view: View) : RecyclerView.ViewHolder(view)