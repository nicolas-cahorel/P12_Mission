package com.openclassrooms.p12m_joiefull

import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.data.model.ItemResult
import com.openclassrooms.p12m_joiefull.data.repository.ItemsRepository
import com.openclassrooms.p12m_joiefull.ui.detailScreen.DetailScreenState
import com.openclassrooms.p12m_joiefull.ui.detailScreen.DetailScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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

@ExperimentalCoroutinesApi
class TestDetailScreenViewModel {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var mockItemsRepository: ItemsRepository
    private lateinit var viewModel: DetailScreenViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun detailScreenViewModel_LoadingState() = runTest {

        // ARRANGE
        val expectedItem = Item(
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
        val expectedResult = ItemResult.Success(expectedItem)
        val expectedState = DetailScreenState.Loading

        `when`(mockItemsRepository.fetchItem(0))
            .thenReturn(flow { emit(expectedResult) })

        val viewModel = DetailScreenViewModel(0, mockItemsRepository, testDispatcher)

        // ACT
        val collectedState = viewModel.detailScreenState.first()

        // ASSERT
        println("TestDetailScreenViewModel : collectedState = $collectedState ")
        println("TestDetailScreenViewModel : expectedState = $expectedState ")
        assertEquals(expectedState, collectedState)
    }

    @Test
    fun detailScreenViewModel_DisplayItemState() = runTest {

        // ARRANGE
        val expectedItem = Item(
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
        val expectedResult = ItemResult.Success(expectedItem)
        val expectedState = DetailScreenState.DisplayDetail(expectedResult.item)

        `when`(mockItemsRepository.fetchItem(0))
            .thenReturn(flow { emit(expectedResult) })

        viewModel = DetailScreenViewModel(0, mockItemsRepository, testDispatcher)

        // ACT
        val collectedState = viewModel.detailScreenState.value

        // ASSERT
        assertEquals(expectedState, collectedState)
    }

    @Test
    fun detailScreenViewModel_ErrorState() = runTest {
        // ARRANGE
        val expectedResult = ItemResult.Error("Item not found")
        val expectedState = DetailScreenState.Error(expectedResult.message)

        `when`(mockItemsRepository.fetchItem(999))
            .thenReturn(flow { emit(expectedResult) })

        viewModel = DetailScreenViewModel(999, mockItemsRepository, testDispatcher)

        // ACT
        val collectedState = viewModel.detailScreenState.value

        // ASSERT
        assertEquals(expectedState, collectedState)
    }

}