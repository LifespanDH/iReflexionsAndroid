package com.lifespandh.ireflexions.home.unity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.navArgs
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseFragment
import com.lifespandh.ireflexions.home.HomeActivity
import com.lifespandh.ireflexions.utils.dialogs.DialogUtils
import com.unity3d.player.UnityPlayer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_unity_exercise.*

class UnityExerciseFragment : BaseFragment() {

    private val args: UnityExerciseFragmentArgs by navArgs()
    private val dialogUtils = DialogUtils()
    private val unityGameObject = "SceneSelect"
    private val unityGameObjectMethod = "SelectEx"

    private var exerciseId: String = ""
    private var unityPlayer: UnityPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_unity_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        getBundleValues()
        setupUnity()
    }

    private fun getBundleValues() {
        exerciseId = args.exerciseId
    }

    private fun setupUnity() {
        unityPlayer = UnityPlayer(requireContext())
        if (unityPlayer != null) {
            unityLayout.addView(
                unityPlayer?.view as View,
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            unityPlayer?.requestFocus()
            unityPlayer?.windowFocusChanged(true)
        } else {
            dialogUtils.showMessage(requireContext(), getString(R.string.exercise_player_not_initialized))
        }

        UnityPlayer.UnitySendMessage(unityGameObject, unityGameObjectMethod, exerciseId)
    }

    override fun onResume() {
        super.onResume()
        unityPlayer?.resume()
        if(exerciseId == "CreatureComfort")
            (context as HomeActivity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    override fun onPause() {
        super.onPause()
        unityPlayer?.pause()
    }

    override fun onDestroy() {
        unityPlayer?.unload()
        unityPlayer = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = UnityExerciseFragment()
    }
}