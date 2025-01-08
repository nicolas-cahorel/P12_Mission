package com.openclassrooms.p12m_joiefull

import android.app.Application
import com.openclassrooms.p12m_joiefull.di.appModule
import com.openclassrooms.p12m_joiefull.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * The main application class for the Joiefull app.
 *
 * This class is used to initialize the Koin dependency injection framework. It provides the
 * application context to Koin and loads the necessary modules to manage dependencies throughout
 * the application's lifecycle.
 */
class MainApplication : Application() {

    /**
     * Called when the application is starting, before any activity, service, or receiver objects
     * (excluding content providers) have been created.
     *
     * This method initializes Koin, which manages dependency injection across the application. It
     * also provides the application context and loads the required modules for dependency management.
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