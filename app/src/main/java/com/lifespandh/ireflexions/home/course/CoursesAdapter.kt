package com.lifespandh.ireflexions.home.course

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.Course
import com.lifespandh.ireflexions.utils.ui.makeInvisible

class CoursesAdapter(
    private var courses: List<Course>,
    private val listener: OnCourseClick
): BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CoursesViewHolder(getView(R.layout.course_item, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CoursesViewHolder)
            holder.bind(courses[position])
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    fun setList(list: List<Course>) {
        this.courses = list
        notifyDataSetChanged()
    }

    inner class CoursesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val courseTitle: TextView = itemView.findViewById(R.id.tvCourseTitle)
        private val courseDescription: TextView = itemView.findViewById(R.id.tvCourseDescription)
        private val courseImage: ImageView = itemView.findViewById(R.id.ivCourseImage)
        private val courseItem: ConstraintLayout = itemView.findViewById(R.id.courseItemView)
        private val courseProgressBar: ProgressBar = itemView.findViewById(R.id.courseProgressBar)

        fun bind(course:Course){
            courseTitle.text = course.name
            courseDescription.text = course.description
            Glide.with(getContext()).load(course.image).into(courseImage)

            courseProgressBar.makeInvisible()

            courseItem.setOnClickListener {
                listener.onCourseClick(course)
            }
        }
    }

    interface OnCourseClick {
        fun onCourseClick(course : Course)
    }
}