package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lifespandh.irefgraphs.ButtonShadowView
import com.lifespandh.irefgraphs.ButtonType
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.howAmI.Happening

class HappeningAdapter(
    private var itemList: List<Happening>,
    private val listener: OnItemClicked
) : BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HappeningViewHolder(getView(R.layout.item_whats_happening, parent))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HappeningAdapter.HappeningViewHolder)
            holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setList(list: List<Happening>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    inner class HappeningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.txt_name)
        private val image: ImageView = itemView.findViewById(R.id.img_item)
        private val btnCircle: ButtonShadowView = itemView.findViewById(R.id.btn_circle_item)


        fun bind(happening:Happening) {
            name.text = happening.name
            if (happening.image != null) {
                Glide.with(getContext()).load(happening.image).into(image)
            }
            if(happening.name == "Panic Attack!") {
                btnCircle.buttonColor = ContextCompat.getColor(
                    getContext(),
                    R.color.red
                )
            }
                else {
                btnCircle.buttonColor = ContextCompat.getColor(
                    getContext(),
                    R.color.whats_happening_item
                )
            }
            btnCircle.buttonType = ButtonType.Circle

        }

    }
    interface OnItemClicked {
        fun onItemClicked(link: String)
    }
}