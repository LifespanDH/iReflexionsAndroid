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
import com.lifespandh.ireflexions.models.PanicAttackSymptoms
import com.lifespandh.ireflexions.models.PanicAttackTrigger

class PanicAttackSymptomsAdapter(
    private var listItems: List<PanicAttackSymptom>,
    private val listener: OnItemClicked
): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PanicAttackSymptomViewHolder(getView(R.layout.item_panic_attack, parent))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PanicAttackSymptomsAdapter.PanicAttackSymptomViewHolder)
            holder.bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun setList(list: List<PanicAttackSymptom>) {
        this.listItems = list
        notifyDataSetChanged()
    }

    inner class PanicAttackSymptomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val symptomButton: ButtonShadowView = itemView.findViewById(R.id.btn_item)

        fun bind(panicAttackSymptom: PanicAttackSymptom) {

            symptomButton.text = panicAttackSymptom.name

            symptomButton.backgroundTintList = ContextCompat.getColorStateList(
                getContext(),
                R.color.unsure_button
            )
            symptomButton.buttonType = ButtonType.Button2to1

            symptomButton.setOnClickListener {
                listener.onItemClicked(panicAttackSymptom)
            }
        }

    }

    interface OnItemClicked {
        fun onItemClicked(panicAttackSymptom: PanicAttackSymptom)
    }
}