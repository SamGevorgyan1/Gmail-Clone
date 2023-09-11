package com.gmailclone

import android.app.Application
import com.gmailclone.di.emailModule
import com.gmailclone.di.emailModuleRepo
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(emailModule, emailModuleRepo)
        }
    }
}