package com.lifespandh.ireflexions.home.course

import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.utils.logs.logE

class ChoicesAdapter(
    private var choices: List<String>,
    private val listener: OnChoiceClicked
): BaseRecyclerViewAdapter() {

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
        private val radioButton: RadioButton = itemView.findViewById(R.id.radioButton)

        fun bind(choice: String) {
            optionTextView.text = choice
        }
    }

    interface OnChoiceClicked {

    }
}