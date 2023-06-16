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
import com.lifespandh.ireflexions.models.PanicAttackSymptom
import com.lifespandh.ireflexions.models.PanicAttackSymptoms
import com.lifespandh.ireflexions.models.PanicAttackTrigger
import com.lifespandh.ireflexions.models.howAmIToday.PanicSymptom
import com.lifespandh.ireflexions.models.howAmIToday.PanicTrigger
import com.lifespandh.ireflexions.utils.removeOrAdd

class PanicAttackSymptomsAdapter(
    private var listItems: MutableList<PanicSymptom>,
    private val listener: OnItemClicked,
    private val howAmITodayViewModel: HowAmITodayViewModel
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

    fun setList(list: List<PanicSymptom>) {
        this.listItems = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addUserCreated(panicSymptom: PanicSymptom) {
        this.listItems.add(this.listItems.size - 1, panicSymptom)
        notifyItemInserted(this.listItems.size - 2)
    }

    inner class PanicAttackSymptomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val symptomButton: ButtonShadowView = itemView.findViewById(R.id.btn_item)

        fun bind(panicAttackSymptom: PanicSymptom) {

            symptomButton.text = panicAttackSymptom.name

            symptomButton.backgroundTintList = ContextCompat.getColorStateList(
                getContext(),
                R.color.unsure_button
            )
            symptomButton.buttonType = ButtonType.Button2to1

            symptomButton.setOnClickListener {
                if (absoluteAdapterPosition != listItems.size - 1)
                    howAmITodayViewModel.selectedPanicSymptoms.removeOrAdd(panicAttackSymptom)

                pushButton(panicAttackSymptom)

                if (absoluteAdapterPosition != listItems.size - 1)
                    listener.onItemClicked(panicAttackSymptom)
                else listener.onCustomSymptomClicked()
            }
        }

        private fun pushButton(panicAttackSymptom: PanicSymptom) {
            symptomButton.isPushed = howAmITodayViewModel.selectedPanicSymptoms.contains(panicAttackSymptom)
            val newColor = if (symptomButton.isPushed)
                R.color.whats_happening_item_pushed
            else
                R.color.unsure_button

            symptomButton.buttonColor = ContextCompat.getColor(getContext(), newColor)
            symptomButton.invalidateView()
        }
    }

    interface OnItemClicked {
        fun onItemClicked(panicAttackSymptom: PanicSymptom)
        fun onCustomSymptomClicked()
    }
}