package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.utils.logs.logE
import com.lifespandh.ireflexions.utils.ui.dp

class EmotionBarAdapter(
    private val emotionEntries: MutableList<MutableMap.MutableEntry<String, JsonElement>>,
    private val totalCount: Int
    ) : BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EmotionViewHolder(getView(R.layout.item_emotion_bar, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmotionViewHolder)
            holder.bind(emotionEntries[position])
    }

    override fun getItemCount(): Int {
        return emotionEntries.size
    }

    inner class EmotionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val barLayout: ConstraintLayout = itemView.findViewById(R.id.barLayout)

        fun bind(mutableEntry: MutableMap.MutableEntry<String, JsonElement>) {
            val color = Color.parseColor(mutableEntry.value.asJsonObject.get("color").asString)
            val count = mutableEntry.value.asJsonObject.get("count").asInt

            barLayout.layoutParams.height = ((count / totalCount.toFloat()) * 30).toInt()
            barLayout.setBackgroundColor(color)
        }
    }
}