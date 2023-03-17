package com.lifespandh.ireflexions.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.lifespandh.ireflexions.R
import com.lifespandh.ireflexions.base.BaseActivity
import com.lifespandh.ireflexions.utils.livedata.observeFreshly
import com.lifespandh.ireflexions.utils.network.OAUTH_KEY
import com.lifespandh.ireflexions.utils.network.createJsonRequestBody
import com.lifespandh.ireflexions.utils.ui.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    private val mGoogleSignInClient by lazy { GoogleSignIn.getClient(this, gso) }
    private val RC_SIGN_IN = 9001

    private val authViewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(
                signInIntent,
                RC_SIGN_IN
            )
        }
    }

    private fun setObservers() {
        authViewModel.tokenLiveData.observeFreshly(this) {
            // We get token here, now we can proceed to the next screen
        }

        authViewModel.errorLiveData.observeFreshly(this) {
            toast(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.

            val requestBody = createJsonRequestBody(OAUTH_KEY to account.idToken)
            authViewModel.loginUser(requestBody)
            Log.d("signInResult", "Successful")

        } catch (e: ApiException) {
            Log.d("signInResult:failed code=", ""+ e.statusCode)
        }
    }
}





