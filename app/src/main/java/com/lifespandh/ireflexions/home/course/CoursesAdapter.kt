package com.lifespandh.ireflexions.home.course

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.Course

class CoursesAdapter(
    private val courses: List<Course>,
    private val listener: OnCourseClick
): BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    interface OnCourseClick {

    }
}