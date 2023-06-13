package com.lifespandh.ireflexions.home.howAmIToday.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lifespandh.irefgraphs.ButtonShadowView
import com.lifespandh.irefgraphs.ButtonType
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.home.howAmIToday.network.HowAmITodayViewModel
import com.lifespandh.ireflexions.models.howAmIToday.EnvironmentalCondition
import com.lifespandh.ireflexions.models.howAmIToday.WhatsHappening
import com.lifespandh.ireflexions.utils.removeOrAdd

class EnvironmentalAdapter(
    private var itemList: MutableList<EnvironmentalCondition>,
    private val listener: OnItemClicked,
    private val howAmITodayViewModel: HowAmITodayViewModel
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

    fun setList(list: List<EnvironmentalCondition>) {
        this.itemList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addUserCreated(environmentalCondition: EnvironmentalCondition) {
        this.itemList.add(this.itemList.size - 1, environmentalCondition)
        notifyItemInserted(this.itemList.size - 2)
    }

    inner class EnvironmentalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val btnEnvironment: ButtonShadowView = itemView.findViewById(R.id.btn_env_item)

        fun bind(environmentalCondition: EnvironmentalCondition){
            btnEnvironment.text = environmentalCondition.name
            btnEnvironment.buttonColor = ContextCompat.getColor(
                getContext(),
                R.color.environment_button
            )

            btnEnvironment.buttonType = ButtonType.Button2to1
            pushButton(environmentalCondition)

            btnEnvironment.setOnClickListener {
                if (absoluteAdapterPosition == this@EnvironmentalAdapter.itemList.size - 1) {
                    listener.onOtherClicked()
                } else {
                    howAmITodayViewModel.selectedEnvironmentalConditions.removeOrAdd(environmentalCondition)

                    pushButton(environmentalCondition)
                }
            }
        }

        private fun pushButton(environmentalCondition: EnvironmentalCondition) {
            btnEnvironment.isPushed = howAmITodayViewModel.selectedEnvironmentalConditions.contains(environmentalCondition)
            btnEnvironment.invalidateView()
        }
    }

    interface OnItemClicked {
        fun onEnvironmentItemClicked()
        fun onOtherClicked()
    }



}