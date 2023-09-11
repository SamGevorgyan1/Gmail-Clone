package com.gmailclone.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.gmailclone.R
import com.gmailclone.model.User
import com.gmailclone.ui.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInHelper private constructor(private val activity: Activity) {

    private val client: GoogleSignInClient
    private val progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)

    init {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        client = GoogleSignIn.getClient(activity, options)
    }

    fun signInWithGoogle() {

        progressBar.visibility = View.VISIBLE

        FirebaseAuth.getInstance().signOut()
        val signInIntent = client.signInIntent
        progressBar.visibility = View.GONE
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                Toast.makeText(
                    activity,
                    "Google sign-in failed: ${e.statusCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        val newUser = User(user.uid, user.displayName ?: "", user.email ?: "")
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.putExtra(Constants.USER, newUser)
                        Log.i("sign in ", "${user.displayName}")
                        activity.startActivity(intent)
                        activity.finish()

                    }
                } else {
                    Toast.makeText(
                        activity,
                        "Firebase authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun signOutAndSignInWithGoogle() {
        FirebaseAuth.getInstance().signOut()
        client.signOut().addOnCompleteListener(activity) {
            signInWithGoogle()
        }
    }

    companion object {
        private var INSTANCE: GoogleSignInHelper? = null
        const val RC_SIGN_IN = 1001

        fun getInstance(activity: Activity): GoogleSignInHelper {
            if (INSTANCE == null) {
                synchronized(GoogleSignInHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = GoogleSignInHelper(activity)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
