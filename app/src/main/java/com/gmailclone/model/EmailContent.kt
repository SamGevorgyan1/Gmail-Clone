package com.gmailclone.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmailContent(
    var id: String? = null,
    val subject: String = " ",
    val sender: String = " ",
    val recipient: String = " ",
    val body: String = " ",
    val timestamp: Long = 1,
    val userName: String? = null,
    val userImage: Int? = null,
    var starred: Boolean = false,
    var isSent: Boolean = false
) : Parcelable