package com.lifespandh.ireflexions.home.care

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseRecyclerViewAdapter
import com.lifespandh.ireflexions.models.CareCenterExercise
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.android.synthetic.main.fragment_lesson_content.image

class CareCenterExerciseAdapter(
    private var exercises: List<CareCenterExercise>
): BaseRecyclerViewAdapter() {

    private var mediaPlayer: MediaPlayer
    private var onTime: Int = 0
    private var playTime: Int = 0
    private var endTime: Int = 0
    private var handler: Handler = Handler()
    var selectedItem = -1
//    private var logoutServiceListner: LogoutServiceListener? = null

    var isMediaPlayerInitialized = false

//    private val mainActivity
//        get() = mContext as MainActivity

    init {
        mediaPlayer = MediaPlayer()
        isMediaPlayerInitialized = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExerciseViewHolder(getView(R.layout.item_care_center_exercise, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExerciseViewHolder)
            holder.bind(exercises[position])
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun setList(list: List<CareCenterExercise>) {
        this.exercises = list
        notifyDataSetChanged()
    }

    /**
     * This function might not be the best way to do this
     * Need to check this and change later, copied rn from previous code to save time
     */
    private fun changeDataSet(position: Int) {
        selectedItem = position
        notifyDataSetChanged()
    }

    fun releaseMediaPlayer() {
        mediaPlayer.release()
        isMediaPlayerInitialized = false
//        mediaPlayer = null
    }

    fun stopPlayer() {
        handler.removeCallbacksAndMessages(null)
        mediaPlayer.stop()
        releaseMediaPlayer()

        playTime = 0
        endTime = 0
        val playingItemIdx = selectedItem
        selectedItem = -1
        notifyItemChanged(playingItemIdx)
    }

    inner class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val titleExercise: TextView = itemView.findViewById(R.id.title_meditation_exercise)
        private val descExercise: TextView = itemView.findViewById(R.id.desc_meditation_exercise)
        private val imgExercise: ImageView = itemView.findViewById(R.id.img_meditation_exercise)
        private val imgPlay: ImageView = itemView.findViewById(R.id.img_play)
        private val imgPause: ImageView = itemView.findViewById(R.id.img_pause)
        private val seekBar: SeekBar = itemView.findViewById(R.id.seekBar)

        fun bind(careCenterExercise: CareCenterExercise) {
            Glide.with(getContext())
                .load(careCenterExercise?.image)
                .into(imgExercise)
            titleExercise.text = careCenterExercise.name
            descExercise.text = careCenterExercise.description

            if (absoluteAdapterPosition != selectedItem) {
                imgPause.visibility = View.INVISIBLE
                imgPlay.visibility = View.VISIBLE
            }


            val updateSongTime = object : Runnable {
                override fun run() {
                    playTime = mediaPlayer.currentPosition

                    seekBar.progress = playTime
                    handler.postDelayed(this, 100)
                }
            }

            imgPlay.setOnClickListener {

                changeDataSet(position)

                imgPlay.visibility = View.INVISIBLE
                imgPause.visibility = View.VISIBLE

                handler.removeCallbacksAndMessages(null)
                mediaPlayer = if (isMediaPlayerInitialized && mediaPlayer.isPlaying) {
                    releaseMediaPlayer()
                    createMediaPlayer(careCenterExercise.audioUrl)

                } else {
                    createMediaPlayer(careCenterExercise.audioUrl)

                }.apply {
                    setOnCompletionListener {
                        handler.removeCallbacksAndMessages(null)
                        seekBar.progress = 0
                        imgPause.visibility = View.INVISIBLE
                        imgPlay.visibility = View.VISIBLE
                    }
                }
                isMediaPlayerInitialized = true

                mediaPlayer.seekTo(seekBar.progress)
                mediaPlayer.start()
                endTime = mediaPlayer.duration
                seekBar.max = endTime
                playTime = mediaPlayer.currentPosition

                handler = Handler()
                handler.postDelayed(updateSongTime, 100)

//                logoutServiceListner?.onStopLogoutTimer()
            }

            imgPause.setOnClickListener {

                imgPause.visibility = View.INVISIBLE
                imgPlay.visibility = View.VISIBLE

                mediaPlayer.pause()

//                logoutServiceListner?.onStartLogoutTimer()

            }


            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    // Display the current progress of SeekBar
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // Do something
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    if (position == selectedItem) {
                        mediaPlayer.seekTo(seekBar.progress)
                    }
                    // Do something

                }
            })

        }

        private fun createMediaPlayer(url: String): MediaPlayer {
            logE("called url $url")
            return MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(url)
                prepare()
            }
        }
    }
}