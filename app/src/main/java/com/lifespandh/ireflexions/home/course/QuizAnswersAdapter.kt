package com.lifespandh.ireflexions.home.course

import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter

class QuizAnswersAdapter(
    private var options: List<String>,
    private val listener: CoursesAdapter.OnCourseClick
): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return QuizAnswersViewHolder(getView(R.layout.item_question, parent))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is QuizAnswersViewHolder)
            holder.bind(options[position])
    }

    override fun getItemCount(): Int {
        return options.size
    }

    fun setList(list: List<String>) {
        this.options = list
        notifyDataSetChanged()
    }

    inner class QuizAnswersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val optionsTV: TextView = itemView.findViewById(R.id.optionsTv)
        private val radioButton: RadioButton = itemView.findViewById(R.id.radioButton)

        fun bind(question: String){
            optionsTV.text = question
        }



    }
}