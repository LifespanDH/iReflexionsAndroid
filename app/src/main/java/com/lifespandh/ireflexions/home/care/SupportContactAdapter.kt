package com.lifespandh.ireflexions.home.care

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.SupportContact

class SupportContactAdapter(
    private var supportContacts: List<SupportContact>,
    private val listener: OnSupportContactClicked
): BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SupportContactViewHolder(getView(R.layout.care_center_contact_item, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SupportContactViewHolder)
            holder.bind(supportContacts[position])
    }

    override fun getItemCount(): Int {
        return supportContacts.size
    }

    fun setList(list: List<SupportContact>) {
        this.supportContacts = list
        notifyDataSetChanged()
    }

    inner class SupportContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val supportContactImage: ImageView = itemView.findViewById(R.id.contact_icon_imageView)
        private val supportContactName: TextView = itemView.findViewById(R.id.contact_name)
        private val callContactButton: Button = itemView.findViewById(R.id.call_contact_button)
        private val textContactButton: Button = itemView.findViewById(R.id.text_contact_button)
        private val moreActionsImageView: ImageView = itemView.findViewById(R.id.more_actions_imageView)

        fun bind(supportContact: SupportContact) {
            supportContactName.text = supportContact.name
            Glide.with(getContext()).load(supportContact.image).into(supportContactImage)

            callContactButton.setOnClickListener{
                listener.callContactClicked(supportContact.phoneNumber)
            }

            textContactButton.setOnClickListener{
                listener.textContactClicked(supportContact.phoneNumber)
            }

            moreActionsImageView.setOnClickListener {
                listener.moreActionsClicked()
            }
        }
    }

    interface OnSupportContactClicked {
        fun callContactClicked(phoneNumber: String)
        fun textContactClicked(phoneNumber: String)
        fun moreActionsClicked()
    }
}