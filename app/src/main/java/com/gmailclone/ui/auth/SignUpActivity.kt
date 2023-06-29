package com.gmailclone.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmailclone.R
import com.gmailclone.databinding.ActivitySigninBinding
import com.gmailclone.ui.auth.fragment.SignInFragment

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                   SignInFragment()
                ).commit()
        }
    }



