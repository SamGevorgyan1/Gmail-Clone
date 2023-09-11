package com.gmailclone.utils

import org.apache.commons.validator.routines.EmailValidator

fun isEmailValid(email: String): Boolean {
    val emailValidator = EmailValidator.getInstance()
    return emailValidator.isValid(email)
}