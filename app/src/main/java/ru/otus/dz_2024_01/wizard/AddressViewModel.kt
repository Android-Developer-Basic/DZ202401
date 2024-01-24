package ru.otus.dz_2024_01.wizard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * View-model for [AddressFragment]
 */
@HiltViewModel
class AddressViewModel @Inject constructor(private val cache: WizardCache) : ViewModel() {

    private val _viewState = MutableStateFlow(render())

    /**
     * View-state for [AddressFragment]
     */
    val viewState: StateFlow<AddressViewState> get() = _viewState.asStateFlow()

    /**
     * Sets address
     */
    fun setAddress(address: String) {
        cache.address = address
        _viewState.value = render()
    }

    /**
     * Renders view-state
     */
    private fun render() = AddressViewState(cache.name, cache.address)
}

/**
 * View-state for [AddressFragment]
 */
data class AddressViewState(val name: String, val address: String)