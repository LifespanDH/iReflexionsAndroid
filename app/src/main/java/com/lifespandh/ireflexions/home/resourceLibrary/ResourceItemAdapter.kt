package com.lifespandh.ireflexions.home.resourceLibrary

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.ResourceLibraryItem

class ResourceItemAdapter(

    private var resourceItems: List<String>,
    private val listener: OnItemClicked

): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ResourceItemViewHolder(getView(R.layout.item_resource, parent))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ResourceItemAdapter.ResourceItemViewHolder)
            holder.bind(resourceItems[position])
    }

    override fun getItemCount(): Int {
        return resourceItems.size
    }

    fun setList(list: List<String>) {
        this.resourceItems = list
        notifyDataSetChanged()
    }

    inner class ResourceItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val resourceLink: TextView = itemView.findViewById(R.id.tvVideoLink)

        fun bind(link: String) {
            resourceLink.text = link
            resourceLink.setOnClickListener {
                listener.onItemClicked(link)
            }
        }
    }

    interface OnItemClicked {
        fun onItemClicked(link: String)
    }

}