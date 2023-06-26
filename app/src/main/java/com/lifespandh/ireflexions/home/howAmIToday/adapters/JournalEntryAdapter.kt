package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.home.course.CourseListProgramAdapter
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.models.howAmIToday.DailyCheckInEntry
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentalCondition
import com.lifespandh.ireflexions.utils.date.getTimeInFormat

class JournalEntryAdapter(
    private var itemList: MutableList<DailyCheckInEntry>,
    private val listener: OnItemClicked
): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JournalEntryViewHolder(getView(R.layout.item_journal_entry, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JournalEntryViewHolder)
            holder.bind(itemList[position])         }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setList(list: List<DailyCheckInEntry>) {
        this.itemList = list.toMutableList()
        notifyDataSetChanged()
    }

    inner class JournalEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtSleepQuality: TextView = itemView.findViewById(R.id.txt_env)
        private val txtMovement: TextView = itemView.findViewById(R.id.txt_movement)
        private val txtSleep: TextView = itemView.findViewById(R.id.txt_sleep)
        private val txtTime: TextView = itemView.findViewById(R.id.txt_time)
        private val imgJournal: ImageView = itemView.findViewById(R.id.img_journal)

        fun bind(dailyCheckInEntry: DailyCheckInEntry) {
            txtMovement.text = getMovementText(dailyCheckInEntry.movement)
            txtSleep.text = "${dailyCheckInEntry.dateTime.substring(14)} hours"
            txtTime.text = "Time: ${dailyCheckInEntry.dateTime.substring(14)}"
            txtSleepQuality.text = dailyCheckInEntry.sleepQuality.quality.toString()

//            Glide.with(getContext()).load(
//                if (dailyCheckInEntry.journalEntry.isEmpty())
//                    R.drawable.no_journal_entry else R.drawable.journal_entry
//            ).into(imgJournal)
        }

    }

    private fun getMovementText(movement: Int): String {
        return when (movement) {
            1, 2 -> getContext().resources.getStringArray(R.array.movementLabels)[0]
            3 -> getContext().resources.getStringArray(R.array.movementLabels)[1]
            4, 5 -> getContext().resources.getStringArray(R.array.movementLabels)[2]
            else -> {
                "Empty"
            }
        }
    }

    interface OnItemClicked {
        fun onItemClick(dailyCheckInEntry: DailyCheckInEntry)
    }
}
