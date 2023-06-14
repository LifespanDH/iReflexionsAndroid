package com.lifespandh.ireflexions.home.course

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.utils.logs.logE
import kotlin.math.abs

class ChoicesAdapter(
    private var choices: List<String>,
    private val listener: OnChoiceClicked
): BaseRecyclerViewAdapter() {

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChoiceViewHolder(getView(R.layout.item_choice, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChoiceViewHolder)
            holder.bind(choices[position])
    }

    override fun getItemCount(): Int {
        return choices.size
    }

    inner class ChoiceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val optionTextView: TextView = itemView.findViewById(R.id.optionTv)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(choice: String) {
            optionTextView.text = choice

            checkBox.isChecked = absoluteAdapterPosition == selectedPosition

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                logE("called $isChecked $selectedPosition $absoluteAdapterPosition")
                val prev = selectedPosition
//                selectedPosition = -1
                if (isChecked) {
                    selectedPosition = absoluteAdapterPosition
                    if (prev != -1)
                        notifyItemChanged(prev)
                } else {
                    if (prev == absoluteAdapterPosition)
                        selectedPosition = -1
                }

                listener.onChoiceSelected(choice, selectedPosition)
            }
        }
    }

    interface OnChoiceClicked {
        fun onChoiceSelected(choice: String, position: Int)
    }
}