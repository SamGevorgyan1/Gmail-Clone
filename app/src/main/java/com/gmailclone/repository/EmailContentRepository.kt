package com.gmailclone.repository

import com.gmailclone.model.EmailContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

interface EmailContentRepository {
    suspend fun getEmailContentById(emailId: String): EmailContent?
    suspend fun getAllEmailContents(): List<EmailContent>
    suspend fun saveEmailContent(emailContent: EmailContent)
    suspend fun updateEmailContent(emailContent: EmailContent)
    suspend fun deleteEmailContent(emailId: String)
}

class EmailContentRepositoryImpl : EmailContentRepository {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val collectionPath = FirebaseAuth.getInstance().currentUser?.email

    override suspend fun getEmailContentById(emailId: String): EmailContent? {
        return withContext(Dispatchers.IO) {
            val documentSnapshot = firestore.collection(collectionPath.toString())
                .document(emailId)
                .get()
                .await()
            return@withContext documentSnapshot.toObject(EmailContent::class.java)
        }
    }

    override suspend fun saveEmailContent(emailContent: EmailContent) {
        withContext(Dispatchers.IO) {
            val documentReference = firestore.collection(collectionPath.toString()).document()
            emailContent.id = documentReference.id
            documentReference.set(emailContent).await()
        }
    }


    override suspend fun updateEmailContent(emailContent: EmailContent) {
        val emailId = emailContent.id
        if (emailId != null) {
            val documentReference =
                firestore.collection(collectionPath.toString()).document(emailId)
            documentReference.set(emailContent).await()
        }
    }

    override suspend fun deleteEmailContent(emailId: String) {
        firestore.collection(collectionPath.toString())
            .document(emailId)
            .delete()
            .await()
    }

    override suspend fun getAllEmailContents(): List<EmailContent> {
        return withContext(Dispatchers.IO) {
            val querySnapshot = firestore.collection(collectionPath.toString())
                .get()
                .await()
            return@withContext querySnapshot.toObjects(EmailContent::class.java)
        }
    }
}