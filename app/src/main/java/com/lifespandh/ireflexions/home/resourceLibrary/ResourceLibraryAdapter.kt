package com.lifespandh.ireflexions.home.resourceLibrary

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.ResourceLibraryItem

class ResourceLibraryAdapter(
    private var resourceLibraryItems: List<ResourceLibraryItem>,
    private val listener: OnItemClicked

): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ResourceLibraryViewHolder(getView(R.layout.item_resource_library, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ResourceLibraryViewHolder)
            holder.bind(resourceLibraryItems[position])
    }

    override fun getItemCount(): Int {
        return resourceLibraryItems.size
    }

    fun setList(list: List<ResourceLibraryItem>) {
        this.resourceLibraryItems = list
        notifyDataSetChanged()
    }

    inner class ResourceLibraryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val resourceName: TextView = itemView.findViewById(R.id.resourceNameTV)
        private val resourceItemContainer: ConstraintLayout = itemView.findViewById(R.id.resourceItemContainer)

        fun bind(resourceLibraryItem: ResourceLibraryItem) {
            resourceName.text = resourceLibraryItem.title

            resourceItemContainer.setOnClickListener {
                listener.onItemClicked(resourceLibraryItem)
            }
        }
    }

    interface OnItemClicked {
        fun onItemClicked(resourceLibraryItem: ResourceLibraryItem)
    }
}