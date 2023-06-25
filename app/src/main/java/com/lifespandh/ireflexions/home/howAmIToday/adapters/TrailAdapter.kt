package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.howAmIToday.TraitSubCategory

class TraitAdapter(
    private var itemList: MutableList<TraitSubCategory>,
    private val listener: OnItemClickedListener
) :
    BaseRecyclerViewAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getView(R.layout.item_trait, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder)
            holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setList(list: List<TraitSubCategory>) {
        this.itemList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun removeOrAdd(traitSubCategory: TraitSubCategory) {
        val index = itemList.indexOf(traitSubCategory)
        if (index == -1) {
            itemList.add(traitSubCategory)
            notifyItemInserted(itemList.size - 1)
        } else {
            itemList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtName: TextView = itemView.findViewById(R.id.traitName)
        private val imgCancel: ImageView = itemView.findViewById(R.id.img_cancel)

        fun bind(traitSubCategory: TraitSubCategory) {
            txtName.text = traitSubCategory.name

            imgCancel.setOnClickListener {
                val index = this@TraitAdapter.itemList.indexOf(traitSubCategory)
                this@TraitAdapter.itemList.removeAt(index)
                notifyItemRemoved(index)

                listener.onCancelClicked(traitSubCategory)
            }

        }
    }

    interface OnItemClickedListener {
        fun onCancelClicked(traitSubCategory: TraitSubCategory)
    }

}