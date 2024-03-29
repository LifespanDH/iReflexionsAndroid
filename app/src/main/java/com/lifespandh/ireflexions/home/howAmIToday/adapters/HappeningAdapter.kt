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
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.WhatsHappening
import com.lifespandh.ireflexions.utils.removeOrAdd

class HappeningAdapter(
    private var itemList: MutableList<WhatsHappening>,
    private val listener: OnItemClicked,
    private val howAmITodayViewModel: HowAmITodayViewModel
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

    fun setList(list: List<WhatsHappening>) {
        this.itemList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addUserCreated(happening: WhatsHappening) {
        this.itemList.add(this.itemList.size - 1, happening)
        notifyItemInserted(this.itemList.size - 2)
    }

    inner class HappeningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.txt_name)
        private val image: ImageView = itemView.findViewById(R.id.img_item)
        private val btnCircle: ButtonShadowView = itemView.findViewById(R.id.btn_circle_item)


        fun bind(happening: WhatsHappening) {
            name.text = happening.name
            Glide.with(getContext()).load(happening.image).into(image)

            btnCircle.buttonType = ButtonType.Circle

            pushButton(happening)

            btnCircle.setOnClickListener {
                if (absoluteAdapterPosition != itemList.size - 1)
                    howAmITodayViewModel.selectedWhatsHappening.removeOrAdd(happening)

                pushButton(happening)


                if (absoluteAdapterPosition == itemList.size - 1)
                    listener.onCustomItemClicked()
                else
                    listener.onItemClicked(happening)
            }
        }

        private fun pushButton(happening: WhatsHappening) {
            btnCircle.isPushed = howAmITodayViewModel.selectedWhatsHappening.contains(happening)
            val newColor = if (btnCircle.isPushed)
                R.color.whats_happening_item_pushed
            else
                if (happening.panicAttack)
                    R.color.red else R.color.whats_happening_item

            btnCircle.buttonColor = ContextCompat.getColor(getContext(), newColor)
            btnCircle.invalidateView()
        }

    }
    interface OnItemClicked {
        fun onItemClicked(happening: WhatsHappening)
        fun onCustomItemClicked()
    }
}