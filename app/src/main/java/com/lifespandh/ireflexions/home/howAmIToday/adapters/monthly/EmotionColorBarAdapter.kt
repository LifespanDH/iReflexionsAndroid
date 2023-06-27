package com.lifespandh.ireflexions.home.howAmIToday.adapters.monthly

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter

//class EmotionColorBarAdapter(
//    private val emotionEntries: MutableList<MutableMap.MutableEntry<String, JsonElement>>,
//): BaseRecyclerViewAdapter() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is EmotionColorViewHolder) {
//            holder.bind(emotionEntries[position])
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return emotionEntries.size
//    }
//
//    fun addToList(emotions: Set<MutableMap.MutableEntry<String, JsonElement>>) {
//        emotions.forEach {
//            emotionEntries[it.key]
//        }
//    }
//
//    inner class EmotionColorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//
//        fun bind(mutableEntry: MutableMap.MutableEntry<String, JsonElement>) {
//
//        }
//    }
//}