package com.lifespandh.ireflexions.home.course

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.Course
import com.lifespandh.ireflexions.models.Lesson

class LessonAdapter(
    private var lessons: List<Lesson>,
    private val listener: OnLessonClick
): BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LessonViewHolder(getView(R.layout.lesson_item, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LessonViewHolder)
            holder.bind(lessons[position])
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    fun setList(list: List<Lesson>) {
        this.lessons = list
        notifyDataSetChanged()
    }

    inner class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val lessonTitle: TextView = itemView.findViewById(R.id.tvLessonName)
        private val lessonDescription: TextView = itemView.findViewById(R.id.tvLessonDescription)
        private val lessonItem: ConstraintLayout = itemView.findViewById(R.id.lessonItem)

        fun bind(lesson:Lesson) {
            lessonTitle.text = lesson.name
            lessonDescription.text = lesson.description

            lessonItem.setOnClickListener {
                listener.onLessonClick(lesson)
            }
        }


    }

    interface OnLessonClick {
        fun onLessonClick(lesson : Lesson)

    }
}