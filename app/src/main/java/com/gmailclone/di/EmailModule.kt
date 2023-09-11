package com.gmailclone.di

import com.gmailclone.repository.EmailContentRepository
import com.gmailclone.repository.EmailContentRepositoryImpl
import com.gmailclone.ui.main.viewmodel.EmailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val emailModuleRepo = module { single<EmailContentRepository> { EmailContentRepositoryImpl() } }

val emailModule = module { viewModel { EmailViewModel(get()) } }

