package com.partos.flashback.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.partos.flashback.MainActivity
import com.partos.flashback.R
import com.partos.flashback.fragments.flashcard.MyFlashcardsFragment
import com.partos.flashback.fragments.packages.AssignPackagesFragment
import com.partos.flashback.models.MyPackage
import kotlinx.android.synthetic.main.row_assign_package.view.*
import kotlinx.android.synthetic.main.row_package.view.*

class AssignPackageRecyclerViewAdapter (var packagesList: ArrayList<MyPackage>) :
    RecyclerView.Adapter<AssignPackageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignPackageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellRow = layoutInflater.inflate(R.layout.row_assign_package, parent, false)
        return AssignPackageViewHolder(cellRow)
    }

    override fun getItemCount(): Int {
        return packagesList.size
    }

    override fun onBindViewHolder(holder: AssignPackageViewHolder, position: Int) {
       holder.view.assign_package_cell_card_view.setOnClickListener {
           val manager = (holder.itemView.context as MainActivity).supportFragmentManager
           manager
               .popBackStack()
       }
    }

}


class AssignPackageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}