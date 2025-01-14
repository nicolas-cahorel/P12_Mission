package com.openclassrooms.p12m_joiefull

import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.data.model.ItemResult
import com.openclassrooms.p12m_joiefull.data.repository.ItemsRepository
import com.openclassrooms.p12m_joiefull.ui.detailScreen.DetailScreenState
import com.openclassrooms.p12m_joiefull.ui.detailScreen.DetailScreenViewModel
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
 * Unit tests for [DetailScreenViewModel].
 * These tests validate the behavior of the ViewModel when interacting with the [ItemsRepository].
 */
@ExperimentalCoroutinesApi
class TestDetailScreenViewModel {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var mockItemsRepository: ItemsRepository
    private lateinit var viewModel: DetailScreenViewModel

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
     * Tests the behavior of [DetailScreenViewModel] when the item is successfully loaded.
     * Validates that the state is updated to [DetailScreenState.DisplayDetail].
     */
    @Test
    fun loadItem_ReturnsState_DisplayItem() = runTest {

        // ARRANGE: Prepare a mocked item and a successful repository result
        val mockedItem = Item(
            id = 0,
            url = "https://xsgames.co/randomusers/assets/avatars/male/0.jpg",
            description = "Pull vert forêt à motif torsadé élégant, tricot finement travaillé avec manches bouffantes et col montant; doux et chalereux.",
            name = "Pull torsadé",
            likes = 24,
            rating = 4.6f,
            price = 69.99,
            originalPrice = 95.00,
            category = "haut"
        )
        val mockedResult = ItemResult.Success(mockedItem)
        val expectedState = DetailScreenState.DisplayDetail(mockedResult.item)

        // Mock the repository to return the mocked result
        `when`(mockItemsRepository.fetchItem(0))
            .thenReturn(flow { emit(mockedResult) })

        // Initialize the ViewModel
        viewModel = DetailScreenViewModel(0, mockItemsRepository, testDispatcher)

        // ACT: Collect the state emitted by the ViewModel
        val collectedState = viewModel.detailScreenState.value

        // ASSERT: Verify that the emitted state matches the expected state
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests the behavior of [DetailScreenViewModel] when an error occurs while loading the item.
     * Validates that the state is updated to [DetailScreenState.Error].
     */
    @Test
    fun loadItem_ReturnsState_Error() = runTest {

        // ARRANGE: Prepare a mocked error result
        val mockedResult = ItemResult.Error("Item not found")
        val expectedState = DetailScreenState.Error(mockedResult.message)

        // Mock the repository to return the mocked error
        `when`(mockItemsRepository.fetchItem(999))
            .thenReturn(flow { emit(mockedResult) })

        // Initialize the ViewModel
        viewModel = DetailScreenViewModel(999, mockItemsRepository, testDispatcher)

        // ACT: Collect the state emitted by the ViewModel
        val collectedState = viewModel.detailScreenState.value

        // ASSERT: Verify that the emitted state matches the expected state
        assertEquals(expectedState, collectedState)
    }

}