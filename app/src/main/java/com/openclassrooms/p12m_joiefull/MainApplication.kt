package com.openclassrooms.p12m_joiefull

import android.app.Application
import com.openclassrooms.p12m_joiefull.di.appModule
import com.openclassrooms.p12m_joiefull.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * The main application class for the Joiefull app.
 *
 * This class is used to initialize the Koin dependency injection framework. The application context
 * is provided to Koin, and the necessary modules are loaded to manage dependencies throughout
 * the application's lifecycle.
 */
class MainApplication : Application() {

    /**
     * Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content providers) have been created.
     *
     * This method is responsible for initializing Koin, which will manage the dependency injection
     * throughout the app. It also sets the application context for Koin and loads the necessary modules.
     */
    override fun onCreate() {
        super.onCreate()
        // Start Koin for dependency injection
        startKoin {
            // Provide the application context to Koin
            androidContext(this@MainApplication)

            // Load the Koin modules that define dependencies
            modules(appModule, dataModule)

            // Enable logging for Koin, useful for debugging purposes
            printLogger()
        }
    }

}