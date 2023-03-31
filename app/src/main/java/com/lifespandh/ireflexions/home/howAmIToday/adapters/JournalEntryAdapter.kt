package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.DailyCheckInEntry

class JournalEntryAdapter(
    private var itemListDailyCheckIn: List<DailyCheckInEntry> = emptyList(),
    private var isChangeColor: Boolean = true
) {

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return JournalViewHolder(getView(R.layout.journal_entry_item, parent))
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val journalItem = itemListDailyCheckIn[position]
//
//        if (isChangeColor) {
//            setColorByEmotion(holder, journalItem)
//        }
//
//        if (journalItem.envConditions.isNotEmpty()) {
//            val envItem = journalItem.envConditions[0].name
//            holder.txtEnv.text =
//                if (journalItem.envConditions.size > 1) "${envItem}, ..." else envItem
//        }
//        holder.txtMovement.text = getMovementText(journalItem.movement)
//        holder.txtSleep.text = "${journalItem.sleepDuration} hours"
//        holder.txtTime.text = sdf.format(parser.parse(journalItem.date))
//
//        holder.imgJournal.setImageResource(
//            if (journalItem.journalEntry.isEmpty())
//                R.drawable.no_journal_entry else R.drawable.journal_entry
//        )
//
//
//        imgList.clear()
//        var count = 0
//        if (journalItem.happeningResults.isNotEmpty()) {
//            for (item in journalItem.happeningResults.filter { !it.name.equals(CREATE_NEW_TEXT_CONST) }) {
//                Log.d(TAG, "happeningResults name: ${item.name} item.isSelected: ${item.isSelected}")
//                if (count >= 5) {
//                    imgList.add(R.drawable.horizontal_dot)
//                    break
//                }
//
//                defaultHappenings.filter { !it.id.equals("15") }.forEach {
//                    if(item.happeningId.equals(it.id) && item.isSelected) {
//                        if (it.id > 15) {
//                            imgList.add(R.drawable.custom_button)
//                            return@forEach
//                        }
//
//                        it.image?.let {
//                            imgList.add(it)
//                        }
//
//                    }
//                }
//                count++
//            }
//        }
//        holder.happeningListView.layoutManager = GridLayoutManager(getContext(), 6)
//
//        val adapter = ImagesAdapter(getContext(), imgList)
//        holder.happeningListView.adapter = adapter
//    }
//
//    private fun setColorByEmotion(holder: ViewHolder, journalItem: DailyCheckInEntry) {
//        val map = HashMap<Int, MutableList<Trait>>()
//        for (i in journalItem.traitCategoryResults) {
//
//            val item = EmotionTraits.categories[i.traitCategoryId].traits
//            map[i.traitCategoryId] = item
//        }
//
//        val list = ArrayList<Int>()
//        for (i in journalItem.traitResults) {
//            if (i.isSelected)
//                list.add(i.traitId)
//        }
//
//        var color: Int = R.color.edt_dark_grey
//        for (item in map) {
//            for (i in map[item.key]!!) {
//                if (list.contains(i.id)) {
//                    color = i.color
//                    break
//                }
//            }
//        }
//
//        if (journalItem.traitCategoryResults.isNotEmpty()) {
//
//            if (journalItem.panicAttack != null) {
//                val colors = intArrayOf(
//                    ContextCompat.getColor(getContext(), color),
//                    ContextCompat.getColor(getContext(), color),
//                    ContextCompat.getColor(getContext(), color),
//                    ContextCompat.getColor(getContext(), R.color.red)
//                )
//                val gd = GradientDrawable(
//                    GradientDrawable.Orientation.BL_TR, colors
//                )
//                gd.cornerRadius = 30f
//                holder.itemView.background = gd
//
//            } else {
//                holder.itemView.backgroundTintList =
//                    ContextCompat.getColorStateList(
//                        getContext(),
//                        color
//                    )
//            }
//
//        } else if (journalItem.panicAttack != null) {
//
//            val colors = intArrayOf(
//                ContextCompat.getColor(getContext(), R.color.edt_dark_grey),
//                ContextCompat.getColor(getContext(), R.color.edt_dark_grey),
//                ContextCompat.getColor(getContext(), R.color.edt_dark_grey),
//                ContextCompat.getColor(getContext(), R.color.red)
//            )
//
//            val gd = GradientDrawable(
//                GradientDrawable.Orientation.BL_TR, colors
//            )
//            gd.cornerRadius = 30f
//
//            holder.itemView.background = gd
//        } else {
//            holder.itemView.backgroundTintList =
//                ContextCompat.getColorStateList(
//                    getContext(),
//                    R.color.edt_dark_grey
//                )
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return itemListDailyCheckIn.size
//    }
//
//    inner class JournalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//
//        var txtEnv: TextView = itemView.findViewById(R.id.txt_env)
//        var txtMovement: TextView = itemView.findViewById(R.id.txt_movement)
//        var txtSleep: TextView = itemView.findViewById(R.id.txt_sleep)
//        var txtTime: TextView = itemView.findViewById(R.id.txt_time)
//        var imgJournal: ImageView = itemView.findViewById(R.id.img_journal)
//        var happeningListView: RecyclerView = itemView.findViewById(R.id.list_happenings)
//
//    }
}