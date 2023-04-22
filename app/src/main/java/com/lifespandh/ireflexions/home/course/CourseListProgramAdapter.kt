package com.lifespandh.ireflexions.home.course

import android.opengl.Visibility
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.Program
import com.lifespandh.ireflexions.utils.logs.logE

class CourseListProgramAdapter(
    private var programs: List<Program>,
    private val listener: OnItemClicked
) : BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProgramViewHolder(getView(R.layout.program_item, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProgramViewHolder)
            holder.bind(programs[position])
    }

    override fun getItemCount(): Int {
        return programs.size
    }

    fun setList(list: List<Program>) {
        this.programs = list
        notifyDataSetChanged()
    }

    inner class ProgramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val myTextView: TextView = itemView.findViewById(R.id.txt_program)
        private val textScore: TextView = itemView.findViewById(R.id.txt_match)
        private val textEnroll: TextView = itemView.findViewById(R.id.txt_enroll)
        private val programContainer: ConstraintLayout = itemView.findViewById(R.id.programContainer)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.programProgressBar)
        private val imgCard: ImageView = itemView.findViewById(R.id.img_program)
        private val guidelineContainerBottom: Guideline = itemView.findViewById(R.id.guidelineContainerBottom)
        private val guidelineContainerEnd: Guideline = itemView.findViewById(R.id.guidelineContainerEnd)
        private val displaymetrics = DisplayMetrics()

        fun bind(program: Program) {
            myTextView.text = program.name
            if(program.img!=null)
            {Glide.with(getContext()).load(program.img).into(imgCard)}
            progressBar.visibility = View.INVISIBLE
            //HomeActivity().windowManager.defaultDisplay.getMetrics(displaymetrics)
//            setLayoutParams(itemView, displaymetrics)
//            guidelineContainerBottom.setGuidelinePercent(1f)
//            guidelineContainerEnd.setGuidelinePercent(0.9f)

            programContainer.setOnClickListener {
                listener.onItemClick(program)
            }

            textEnroll.setOnClickListener {
                listener.onProgramEnroll(program)
            }
        }

    }

    private fun setLayoutParams(view: View, displaymetrics: DisplayMetrics){

        val screenWidth = displaymetrics.widthPixels
        val itemWidth = screenWidth / 1.9

        val lp = view.layoutParams
        lp.width = itemWidth.toInt()
        view.layoutParams = lp
    }

    interface OnItemClicked {
        fun onItemClick(program: Program)
        fun onProgramEnroll(program: Program)
    }
}