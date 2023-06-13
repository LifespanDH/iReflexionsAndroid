package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.content.Context
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
    private var itemList: List<TraitSubCategory>,
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
        this.itemList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val txtName: TextView = itemView.findViewById(R.id.traitName)
        private val imgCancel: ImageView = itemView.findViewById(R.id.img_cancel)

        fun bind(traitSubCategory: TraitSubCategory) {
            txtName.text = traitSubCategory.name

            imgCancel.setOnClickListener {
//                listener.onItemClick(absoluteAdapterPosition, )
            }

        }

        override fun onClick(p0: View?) {
            listener.onItemClick(adapterPosition, this)
        }

    }

    interface OnItemClickedListener {
        fun onItemClick(position: Int, viewHolder: ViewHolder)

    }

}