package ru.otus.dz_2024_01.wizard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * View-model for [AddressFragment]
 */
@HiltViewModel
class AddressViewModel @Inject constructor(private val cache: WizardCache) : ViewModel() {
    /**
     * View-state for [AddressFragment]
     */
    val viewState: StateFlow<AddressViewState> get() = cache.state
        .map { render(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, render(cache.state.value))

    /**
     * Sets address
     */
    fun setAddress(address: String) {
        cache.setAddress(address)
    }

    /**
     * Renders view-state
     */
    private fun render(data: RegData) = AddressViewState(data.address)
}

/**
 * View-state for [AddressFragment]
 */
data class AddressViewState(val address: String)