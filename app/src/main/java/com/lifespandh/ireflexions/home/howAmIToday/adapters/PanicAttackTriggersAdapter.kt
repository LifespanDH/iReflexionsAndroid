package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.irefgraphs.ButtonShadowView
import com.lifespandh.irefgraphs.ButtonType
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.PanicTrigger
import com.lifespandh.ireflexions.utils.removeOrAdd

class PanicAttackTriggersAdapter(
    private var listItems: List<PanicTrigger>,
    private val listener: OnItemClicked,
    private val howAmITodayViewModel: HowAmITodayViewModel
): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PanicAttackTriggerViewHolder(getView(R.layout.item_panic_attack, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PanicAttackTriggersAdapter.PanicAttackTriggerViewHolder)
            holder.bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun setList(list: List<PanicTrigger>) {
        this.listItems = list
        notifyDataSetChanged()
    }

    inner class PanicAttackTriggerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val triggerButton: ButtonShadowView = itemView.findViewById(R.id.btn_item)

        fun bind(panicAttackTrigger: PanicTrigger) {

            triggerButton.text = panicAttackTrigger.name
            triggerButton.buttonColor = ContextCompat.getColor(
                getContext(),
                R.color.environment_button
            )
            triggerButton.buttonType = ButtonType.Button2to1

            triggerButton.setOnClickListener {
                if (absoluteAdapterPosition != listItems.size - 1)
                    howAmITodayViewModel.selectedPanicTriggers.removeOrAdd(panicAttackTrigger)

                pushButton(panicAttackTrigger)

                if (absoluteAdapterPosition != listItems.size - 1)
                    listener.onItemClicked(panicAttackTrigger)
                else listener.onCustomTriggerClicked()
            }

        }

        private fun pushButton(panicTrigger: PanicTrigger) {
            triggerButton.isPushed = howAmITodayViewModel.selectedPanicTriggers.contains(panicTrigger)
            val newColor = if (triggerButton.isPushed)
                R.color.whats_happening_item_pushed
            else
                R.color.environment_button

            triggerButton.buttonColor = ContextCompat.getColor(getContext(), newColor)
            triggerButton.invalidateView()
        }
    }

    interface OnItemClicked {
        fun onItemClicked(panicAttackTrigger: PanicTrigger)
        fun onCustomTriggerClicked()
    }


}