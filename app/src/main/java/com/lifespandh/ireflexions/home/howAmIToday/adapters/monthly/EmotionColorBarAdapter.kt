package com.lifespandh.ireflexions.home.howAmIToday.adapters.monthly

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.howAmIToday.EmotionData

class EmotionColorBarAdapter(
    private var emotionEntries: MutableMap<String, EmotionData>,
    private var totalCount: Int = 0
): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EmotionColorViewHolder(getView(R.layout.item_emotion_color_bar, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmotionColorViewHolder) {
            val key = emotionEntries.keys.toList()[position]
            holder.bind(emotionEntries[key], key)
        }
    }

    override fun getItemCount(): Int {
        return emotionEntries.size
    }

    fun addToList(emotions: MutableMap<String, EmotionData>, totalCount: Int) {
        this.emotionEntries = emotions
        this.totalCount = totalCount
        notifyDataSetChanged()
    }

    inner class EmotionColorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        private val categoryCount: TextView = itemView.findViewById(R.id.categoryCount)

        private val categoryBar: ProgressBar = itemView.findViewById(R.id.categoryBar)

        fun bind(mutableEntry: EmotionData?, name: String) {
            categoryName.text = name
            categoryCount.text = mutableEntry?.first?.count?.toString()

            categoryBar.progress = (((mutableEntry?.first?.count ?: 0) / totalCount.toFloat()) * 100).toInt()
            categoryBar.progressTintList = ColorStateList.valueOf(Color.parseColor(mutableEntry?.first?.color))
        }
    }
}