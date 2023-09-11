package com.gmailclone.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmailclone.model.EmailContent
import androidx.lifecycle.viewModelScope
import com.gmailclone.repository.EmailContentRepository
import kotlinx.coroutines.launch

class EmailViewModel(private val repository: EmailContentRepository) : ViewModel() {

    private val _emailList = MutableLiveData<List<EmailContent>>()
    val emailList: LiveData<List<EmailContent>> get() = _emailList

    private val _sentEmailList = MutableLiveData<List<EmailContent>?>()
    val sentEmailList: LiveData<List<EmailContent>?> get() = _sentEmailList

    private val _selectedEmail = MutableLiveData<EmailContent?>()
    val selectedEmail: MutableLiveData<EmailContent?> get() = _selectedEmail

    fun getAllEmails() {
        viewModelScope.launch {
            val emails = repository.getAllEmailContents()
            _emailList.value = emails
        }
    }

    fun getEmailById(emailId: String) {
        viewModelScope.launch {
            val email = repository.getEmailContentById(emailId)
            email?.starred = true
            _selectedEmail.value = email
        }
    }

    fun sendEmail(emailContent: EmailContent) {
        viewModelScope.launch {
            repository.saveEmailContent(emailContent)
        }
    }

    fun updateEmail(emailContent: EmailContent) {
        viewModelScope.launch {
            emailContent.starred = true
            repository.updateEmailContent(emailContent)
        }
    }

    fun deleteEmail(emailId: String) {
        viewModelScope.launch {
            repository.deleteEmailContent(emailId)
        }
    }
}
