package com.openclassrooms.p12m_joiefull

import com.openclassrooms.p12m_joiefull.data.apiResponse.ItemApiResponse
import com.openclassrooms.p12m_joiefull.data.apiResponse.PictureApiResponse
import com.openclassrooms.p12m_joiefull.data.apiResponse.categorizedItems
import com.openclassrooms.p12m_joiefull.data.apiResponse.toItems
import com.openclassrooms.p12m_joiefull.data.model.CategoriesResult
import com.openclassrooms.p12m_joiefull.data.model.ItemResult
import com.openclassrooms.p12m_joiefull.data.network.ItemsClient
import com.openclassrooms.p12m_joiefull.data.repository.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
import retrofit2.Response

/**
 * Unit tests for [ItemsRepository].
 * These tests validate the behavior of the repository when interacting with the [ItemsClient].
 */
@ExperimentalCoroutinesApi
class TestItemsRepository {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var mockItemsClient: ItemsClient
    private lateinit var itemsRepository: ItemsRepository

    /**
     * Sets up the test environment before each test.
     * Initializes the mock objects and sets the main coroutine dispatcher to the test dispatcher.
     */
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        itemsRepository = ItemsRepository(mockItemsClient)
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
     * Tests [ItemsRepository.fetchCategories] when the API returns a successful response with data.
     * Validates that the result is a [CategoriesResult.Success].
     */
    @Test
    fun fetchCategories_ReturnsState_Success() = runTest {

        // ARRANGE: Prepare a successful mocked API response
        val mockedItemsApiResponse = listOf(
            ItemApiResponse(
                id = 1,
                picture = PictureApiResponse(
                    url = "https://xsgames.co/randomusers/assets/avatars/male/1.jpg",
                    description = "A product image"
                ),
                name = "Product 1",
                category = "category1",
                likes = 120,
                price = 19.99,
                originalPrice = 24.99
            )
        )
        val mockedApiResponse = Response.success(mockedItemsApiResponse)
        val expectedState = CategoriesResult.Success(mockedItemsApiResponse.categorizedItems())

        // Mock the API client to return the mocked response
        `when`(mockItemsClient.getItems()).thenReturn(mockedApiResponse)

        // ACT: Fetch categories from the repository
        val collectedState = itemsRepository.fetchCategories().first()

        // ASSERT: Verify the returned state matches the expected state
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests [ItemsRepository.fetchCategories] when the API returns a successful response with no data.
     * Validates that the result is a [CategoriesResult.Error] indicating no products are available.
     */
    @Test
    fun fetchCategories_ReturnsState_ErrorEmpty() = runTest {

        // ARRANGE: Prepare a mocked API response with an empty list
        val mockedItemsApiResponse = emptyList<ItemApiResponse>()
        val mockedApiResponse = Response.success(mockedItemsApiResponse)
        val expectedState = CategoriesResult.Error("No products available.")

        // Mock the API client to return the mocked response
        `when`(mockItemsClient.getItems()).thenReturn(mockedApiResponse)

        // ACT: Fetch categories from the repository
        val collectedState = itemsRepository.fetchCategories().first()

        // ASSERT: Verify the returned state matches the expected state
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests [ItemsRepository.fetchCategories] when the API returns an error response.
     * Validates that the result is a [CategoriesResult.Error] indicating a server error occurred.
     */
    @Test
    fun fetchCategories_ReturnsState_ErrorServer() = runTest {

        // ARRANGE: Prepare a mocked API response with an error code
        val mockedApiResponse = Response.error<List<ItemApiResponse>>(
            404,
            okhttp3.ResponseBody.create(null, "")
        )
        val expectedState = CategoriesResult.Error("Server error. Please try again later.")

        // Mock the API client to return the mocked error response
        `when`(mockItemsClient.getItems()).thenReturn(mockedApiResponse)

        // ACT: Fetch categories from the repository
        val collectedState = itemsRepository.fetchCategories().first()

        // ASSERT: Verify the returned state matches the expected state
        assertEquals(expectedState, collectedState)
    }

    /**
     * Validates that [ItemsRepository.fetchItem] returns a [ItemResult.Success]
     * when the requested item is found in a successful API response.
     */
    @Test
    fun fetchItem_ReturnsState_Success() = runTest {

        // ARRANGE: Prepare a successful mocked API response
        val mockedItemsApiResponse = listOf(
            ItemApiResponse(
                id = 1,
                picture = PictureApiResponse(
                    url = "https://xsgames.co/randomusers/assets/avatars/male/1.jpg",
                    description = "A product image"
                ),
                name = "Product 1",
                category = "category1",
                likes = 120,
                price = 19.99,
                originalPrice = 24.99
            )
        )
        val mockedApiResponse = Response.success(mockedItemsApiResponse)
        val expectedState =
            ItemResult.Success(mockedItemsApiResponse.toItems().find { it.id == 1 }!!)

        // Mock the API client to return the mocked response
        `when`(mockItemsClient.getItems()).thenReturn(mockedApiResponse)

        // ACT: Fetch item by ID from the repository
        val collectedState = itemsRepository.fetchItem(1).first()

        // ASSERT: Verify the returned state matches the expected state
        assertEquals(expectedState, collectedState)
    }

    /**
     * Validates that [ItemsRepository.fetchItem] returns a [ItemResult.Error]
     * when the requested item is not found in a successful API response.
     */
    @Test
    fun fetchItem_ReturnsState_ErrorNotFound() = runTest {

        // ARRANGE: Prepare a mocked API response with a different item
        val mockedItemsApiResponse = listOf(
            ItemApiResponse(
                id = 2,
                picture = PictureApiResponse(
                    url = "https://xsgames.co/randomusers/assets/avatars/male/2.jpg",
                    description = "Another product image"
                ),
                name = "Product 2",
                category = "category2",
                likes = 80,
                price = 15.99,
                originalPrice = 20.99
            )
        )
        val mockedApiResponse = Response.success(mockedItemsApiResponse)
        val expectedState = ItemResult.Error("Product not found.")

        // Mock the API client to return the mocked response
        `when`(mockItemsClient.getItems()).thenReturn(mockedApiResponse)

        // ACT: Fetch item by ID from the repository
        val collectedState = itemsRepository.fetchItem(1).first()

        // ASSERT: Verify the returned state matches the expected state
        assertEquals(expectedState, collectedState)
    }

    /**
     * Validates that [ItemsRepository.fetchItem] returns a [ItemResult.Error]
     * when the API responds with a server-side error (e.g., HTTP 5xx).
     */
    @Test
    fun fetchItem_ReturnsState_ErrorServer() = runTest {

        // ARRANGE: Prepare a mocked API response with an error code
        val mockedApiResponse = Response.error<List<ItemApiResponse>>(
            500,
            okhttp3.ResponseBody.create(null, "")
        )
        val expectedState = ItemResult.Error("An unexpected error occurred.")

        // Mock the API client to return the mocked error response
        `when`(mockItemsClient.getItems()).thenReturn(mockedApiResponse)

        // ACT: Fetch item by ID from the repository
        val collectedState = itemsRepository.fetchItem(1).first()

        // ASSERT: Verify the returned state matches the expected state
        assertEquals(expectedState, collectedState)
    }

}