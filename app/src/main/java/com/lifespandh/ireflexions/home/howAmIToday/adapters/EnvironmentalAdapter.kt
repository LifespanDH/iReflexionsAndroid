package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.irefgraphs.ButtonShadowView
import com.lifespandh.irefgraphs.ButtonType
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentCondition

class EnvironmentalAdapter(
    private var itemList: List<EnvironmentCondition>,
    private val listener: EnvironmentalAdapter.OnItemClicked
): BaseRecyclerViewAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EnvironmentalViewHolder(getView(R.layout.item_environmental, parent))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EnvironmentalAdapter.EnvironmentalViewHolder)
            holder.bind(itemList[position])       }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setList(list: List<EnvironmentCondition>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    inner class EnvironmentalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val btnEnvironment: ButtonShadowView = itemView.findViewById(R.id.btn_env_item)

        fun bind(environmentalCondition: EnvironmentCondition){
            btnEnvironment.text = environmentalCondition.name
            btnEnvironment.buttonColor = ContextCompat.getColor(
                getContext(),
                R.color.environment_button
            )

            btnEnvironment.buttonType = ButtonType.Button2to1

        }
    }

    interface OnItemClicked {
        fun onEnvironmentItemClicked()
    }



}