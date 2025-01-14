package com.openclassrooms.p12m_joiefull

import com.openclassrooms.p12m_joiefull.data.model.CategoriesResult
import com.openclassrooms.p12m_joiefull.data.model.Category
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.data.repository.ItemsRepository
import com.openclassrooms.p12m_joiefull.ui.homeScreen.HomeScreenState
import com.openclassrooms.p12m_joiefull.ui.homeScreen.HomeScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [HomeScreenViewModel].
 * These tests validate the behavior of the ViewModel when interacting with the [ItemsRepository].
 */
@ExperimentalCoroutinesApi
class TestHomeScreenViewModel {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var mockItemsRepository: ItemsRepository
    private lateinit var viewModel: HomeScreenViewModel

    /**
     * Sets up the test environment before each test.
     * Initializes the mock objects and sets the main coroutine dispatcher to the test dispatcher.
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }

    /**
     * Cleans up the test environment after each test.
     * Resets the main coroutine dispatcher to avoid affecting other tests.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Tests the behavior of [HomeScreenViewModel] when categories are successfully loaded.
     * Validates that the state is updated to [HomeScreenState.DisplayItems].
     */
    @Test
    fun loadCategories_ReturnsState_DisplayItems() = runTest {

        // ARRANGE: Prepare mocked categories and a successful repository result
        val mockCategories = listOf(
            Category(
                name = "category1",
                items = listOf(
                    Item(
                        id = 1,
                        url = "https://xsgames.co/randomusers/assets/avatars/male/1.jpg",
                        description = "Product 1 Image",
                        name = "Product 1",
                        likes = 120,
                        rating = 4.5f,
                        price = 19.99,
                        originalPrice = 24.99,
                        category = "category1"
                    )
                )
            ),
            Category(
                name = "category2",
                items = listOf(
                    Item(
                        id = 2,
                        url = "https://xsgames.co/randomusers/assets/avatars/female/2.jpg",
                        description = "Product 2 Image",
                        name = "Product 2",
                        likes = 75,
                        rating = 3.8f,
                        price = 14.99,
                        originalPrice = 19.99,
                        category = "category2"
                    )
                )
            )
        )
        val mockedResult = CategoriesResult.Success(mockCategories)
        val expectedState = HomeScreenState.DisplayItems(mockedResult.categories)

        // Mock the repository to return the mocked result
        `when`(mockItemsRepository.fetchCategories())
            .thenReturn(flow { emit(mockedResult) })

        // Initialize the ViewModel
        viewModel = HomeScreenViewModel(mockItemsRepository, testDispatcher)

        // ACT: Collect the state emitted by the ViewModel
        val collectedState = viewModel.homeScreenState.value

        // ASSERT: Verify that the emitted state matches the expected state
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests the behavior of [HomeScreenViewModel] when an error occurs while loading categories.
     * Validates that the state is updated to [HomeScreenState.Error].
     */
    @Test
    fun loadCategories_ReturnsState_Error() = runTest {

        // ARRANGE: Prepare a mocked error result
        val mockedResult = CategoriesResult.Error("No products available.")
        val expectedState = HomeScreenState.Error(mockedResult.message)

        // Mock the repository to return the mocked error
        `when`(mockItemsRepository.fetchCategories())
            .thenReturn(flow { emit(mockedResult) })

        // Initialize the ViewModel
        viewModel = HomeScreenViewModel(mockItemsRepository, testDispatcher)

        // ACT: Collect the state emitted by the ViewModel
        val collectedState = viewModel.homeScreenState.value

        // ASSERT: Verify that the emitted state matches the expected state
        assertEquals(expectedState, collectedState)
    }

}