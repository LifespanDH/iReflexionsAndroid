package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.irefgraphs.ButtonShadowView
import com.lifespandh.irefgraphs.ButtonType
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.PanicAttackSymptom
import com.lifespandh.ireflexions.models.PanicAttackTrigger
import com.lifespandh.ireflexions.models.howAmIToday.PanicTrigger

class PanicAttackTriggersAdapter(
    private var listItems: List<PanicTrigger>,
    private val listener: OnItemClicked
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
                listener.onItemClicked(panicAttackTrigger)
            }

        }

    }

    interface OnItemClicked {
        fun onItemClicked(panicAttackTrigger: PanicTrigger)
    }


}