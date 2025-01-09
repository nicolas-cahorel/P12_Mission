package com.openclassrooms.p12m_joiefull.di

import com.openclassrooms.p12m_joiefull.data.repository.ItemsRepository
import com.openclassrooms.p12m_joiefull.ui.details.DetailScreenViewModel
import com.openclassrooms.p12m_joiefull.ui.products.HomeScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Dependency injection module for the application using Koin.
 *
 * This module provides the required dependencies such as repositories, view models,
 * and coroutine scopes for the application. It ensures proper initialization
 * and lifecycle management of these components.
 */
val appModule = module {

    /**
     * Provides a [CoroutineScope] for the application.
     *
     * This scope is created with a [SupervisorJob] and uses the [Dispatchers.Main] dispatcher.
     * It is intended for managing UI-related coroutine tasks and ensuring error isolation.
     *
     * @return A [CoroutineScope] instance suitable for UI-level coroutines.
     */
    single {
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    /**
     * Provides a singleton instance of [ItemsRepository].
     *
     * The repository is responsible for handling data operations, including
     * fetching data from the API and processing it. It interacts with the `ItemsClient` to
     * retrieve item data and provides domain models to the application.
     *
     * @return A singleton instance of [ItemsRepository].
     */
    single {
        ItemsRepository(get())
    }

    /**
     * Provides the [HomeScreenViewModel] instance.
     *
     * This view model manages the logic for the home screen, interacting with the [ItemsRepository]
     * to fetch and display categorized item data. It is lifecycle-aware and designed to survive
     * configuration changes, such as screen rotations.
     *
     * @return A [HomeScreenViewModel] instance.
     */
    viewModel {
        HomeScreenViewModel(get())
    }



    /**
     * Provides the [DetailScreenViewModel] instance.
     *
     * This view model is responsible for handling the logic of the detail screen. It interacts with
     * the [ItemsRepository] to fetch specific item details and supports various user interactions.
     * It is lifecycle-aware and ensures data persistence during configuration changes.
     *
     * @return A [DetailScreenViewModel] instance.
     */
//    viewModel {
//        DetailScreenViewModel(get())
//    }

    viewModel { (itemId: Int) -> DetailScreenViewModel(itemId,get()) }

}