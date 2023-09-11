package com.gmailclone.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmailclone.databinding.ActivitySigninBinding
import com.gmailclone.utils.GoogleSignInHelper

class SignUpActivity : AppCompatActivity() {

    private lateinit var googleSignInHelper: GoogleSignInHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        googleSignInHelper = GoogleSignInHelper.getInstance(this)

        binding.btnGoogle.setOnClickListener {
            googleSignInHelper.signInWithGoogle()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleSignInHelper.handleActivityResult(requestCode, resultCode, data)
    }
}
