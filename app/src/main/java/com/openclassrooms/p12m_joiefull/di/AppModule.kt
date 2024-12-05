package com.openclassrooms.p12m_joiefull.di

import com.openclassrooms.p12m_joiefull.data.repository.ItemsRepository
import com.openclassrooms.p12m_joiefull.ui.ItemsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for providing dependencies in the application.
 *
 * This module defines how to provide instances of different components such as repositories,
 * view models, and coroutine scopes for the app. It uses Koin for dependency injection.
 */
val appModule = module {

    /**
     * Provides a CoroutineScope for the application.
     *
     * This scope combines a SupervisorJob with the Main dispatcher,
     * making it suitable for managing UI-related tasks and handling errors in coroutines.
     *
     * @return A [CoroutineScope] for the application.
     */
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    /**
     * Provides the ItemsRepository using the ItemsClient.
     *
     * The repository is responsible for fetching data from the API and processing it.
     * This instance will be injected wherever an [ItemsRepository] is needed.
     *
     * @return An instance of [ItemsRepository] that interacts with the API client.
     */
    single {
        ItemsRepository(get())
    }

    /**
     * Provides the ItemsViewModel.
     *
     * The view model interacts with the [ItemsRepository] to fetch and display item data.
     * It is lifecycle-aware and provides data to the UI layer.
     *
     * @return An instance of [ItemsViewModel] that handles UI-related logic for items.
     */
    viewModel {
        ItemsViewModel(get())
    }

}