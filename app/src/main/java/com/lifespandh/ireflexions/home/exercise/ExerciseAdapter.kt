package com.lifespandh.ireflexions.home.exercise

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.Exercise

class ExerciseAdapter(
    private val exercises: List<Exercise>
): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExerciseViewHolder(getView(R.layout.item_exercise, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExerciseViewHolder)
            holder.bind(exercises[position])
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    inner class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val exerciseImage: ImageView = itemView.findViewById(R.id.exerciseImage)
        private val exerciseTitle: TextView = itemView.findViewById(R.id.exerciseTitle)
        private val exerciseDescription: TextView = itemView.findViewById(R.id.exerciseDescription)

        fun bind(exercise: Exercise) {
            exerciseTitle.text = exercise.name
            exerciseDescription.text = exercise.description
            Glide.with(getContext()).load(exercise.image).into(exerciseImage)
        }
    }
}