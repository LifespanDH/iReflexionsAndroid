package com.lifespandh.ireflexions.home.course

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.LessonQuestion
import com.lifespandh.ireflexions.models.QUESTION_TYPE
import com.lifespandh.ireflexions.utils.ui.makeGone
import com.lifespandh.ireflexions.utils.ui.makeVisible
import kotlinx.android.synthetic.main.item_multiplechoice.view.answersRecyclerView

class QuestionsAdapter(
    private var questions: List<LessonQuestion>,
    private val listener: OnAnswerSelected
): BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return QuestionViewHolder(getView(R.layout.item_quiz, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is QuestionViewHolder)
            holder.bind(questions[position])
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    fun setList(list: List<LessonQuestion>) {
        this.questions = list
        notifyDataSetChanged()
    }

    inner class QuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val questionText: TextView = itemView.findViewById(R.id.question_text)
        private val customAnswerEditText: EditText = itemView.findViewById(R.id.customAnswerEditText)

        private val trueFalseContainer: ConstraintLayout = itemView.findViewById(R.id.trueFalseContainer)
        private val multipleChoiceContainer: ConstraintLayout = itemView.findViewById(R.id.multipleChoiceContainer)

        fun bind(question: LessonQuestion) {

            questionText.text = question.question

            multipleChoiceContainer.makeGone()
            trueFalseContainer.makeGone()
            customAnswerEditText.makeGone()

            when (question.questionType) {
                QUESTION_TYPE.MULTIPLE_CHOICE.type -> {
                    multipleChoiceContainer.makeVisible()
                    setAnswersRecyclerView(question.answers)
                }

                QUESTION_TYPE.TRUE_FALSE.type -> {
                    trueFalseContainer.makeVisible()
                }

                QUESTION_TYPE.INPUT.type -> {
                    customAnswerEditText.makeVisible()
                }

                else -> {
                    multipleChoiceContainer.makeVisible()
                    setAnswersRecyclerView(question.answers)
                }
            }
        }

        private fun setAnswersRecyclerView(answers: List<String>) {
            multipleChoiceContainer.answersRecyclerView.apply {
                adapter = ChoicesAdapter(answers, object : ChoicesAdapter.OnChoiceClicked {

                })
                layoutManager = LinearLayoutManager(this@QuestionsAdapter.getContext())
                isVisible = true
            }
        }
    }

    interface OnAnswerSelected {

    }
}