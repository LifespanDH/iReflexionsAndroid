package com.lifespandh.ireflexions.home.howAmIToday.adapters.monthly

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonElement
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.PanicAttack
import com.lifespandh.ireflexions.utils.date.DATE_TIME_FORMAT
import com.lifespandh.ireflexions.utils.date.DATE_TIME_MILLI_LONG_FORMAT
import com.lifespandh.ireflexions.utils.date.changeDateTimeFormat
import com.lifespandh.ireflexions.utils.date.getDateInHumanFormat
import com.lifespandh.ireflexions.utils.deserializeFromJson
import com.lifespandh.ireflexions.utils.logs.logD
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date

class PanicAttackListAdapter(
    private val panicEntries: MutableList<Pair<String, JsonElement>>,
    private val listener: OnItemClicked
): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PanicAttackViewHolder(getView(R.layout.item_panic_attack_text, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PanicAttackViewHolder)
            holder.bind(panicEntries[position])
    }

    override fun getItemCount(): Int {
        return panicEntries.size
    }

    fun addToList(newList: MutableList<JsonElement>?, date: LocalDate) {
        newList?.forEach {
            val calendar = Calendar.getInstance()
            calendar.time = Date.from(date?.atStartOfDay()?.toInstant(ZoneOffset.UTC))
            panicEntries.add(Pair(getDateInHumanFormat(calendar), it))
        }
        notifyDataSetChanged()
    }

    inner class PanicAttackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val panicAttackText: TextView = itemView.findViewById(R.id.panicAttackText)

        fun bind(pair: Pair<String, JsonElement>) {
            panicAttackText.text = "${pair.first} ${pair.second.asJsonObject["time"].asString.changeDateTimeFormat(DATE_TIME_MILLI_LONG_FORMAT, DATE_TIME_FORMAT)}"
            var panic = pair.second.deserializeFromJson(PanicAttack::class.java)
            logD("${panic?.time} ${panic?.intensity} ${panic?.symptomResults} ${panic?.triggerResults} ${pair.second} ")
            panicAttackText.setOnClickListener {
                listener.onPanicItemClick(pair)
            }
        }
    }

    interface OnItemClicked {
        fun onPanicItemClick(pair: Pair<String, JsonElement>)
    }
}