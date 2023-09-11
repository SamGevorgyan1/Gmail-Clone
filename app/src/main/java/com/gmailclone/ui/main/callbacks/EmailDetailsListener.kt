package com.gmailclone.ui.main.callbacks

import com.gmailclone.model.EmailContent

interface EmailDetailsListener {
    fun openEmailDetail(fragmentId: Int? = null, emailContent: EmailContent? = null)
    fun closeEmailDetail()
    fun openCompose(fragmentId: Int?=null, emailContent: EmailContent?=null)
    fun closeCompose()
}