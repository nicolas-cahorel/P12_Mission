package com.openclassrooms.p12m_joiefull.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.p12m_joiefull.data.model.Item
import com.openclassrooms.p12m_joiefull.ui.products.ProductsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val productsViewModel: ProductsViewModel
    ) : ViewModel() {

    private val _item = MutableStateFlow<Item?>(null)
    val item: StateFlow<Item?> get() = _item

    fun loadItemById(itemId: Int) {
        viewModelScope.launch {
            val item = productsViewModel.categories.value
                .flatMap { category -> category.items }
                .find { it.id == itemId }

            _item.value = item

            if (item != null) {
                Log.d("DetailsViewModel", "Loaded item: ${item.id}, name: ${item.name}")
            } else {
                Log.d("DetailsViewModel", "Item not found or list is empty")
            }
        }
    }
}