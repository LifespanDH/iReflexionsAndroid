package com.lifespandh.ireflexions.home.course

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.Lesson

class LessonAdapter(
    private val lessons: List<Lesson>,
    private val listener: OnLessonClick
): BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    interface OnLessonClick {

    }
}