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
 * View-model for [NameFragment]
 */
@HiltViewModel
class NameViewModel @Inject constructor(private val cache: WizardCache) : ViewModel() {
    /**
     * View-state for [NameFragment]
     */
    val viewState: StateFlow<NameViewState> = cache.state
        .map { render(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, render(cache.state.value))

    /**
     * Sets name
     */
    fun setName(name: String) {
        cache.setName(name)
    }

    /**
     * Renders view-state
     */
    private fun render(data: RegData) = NameViewState(data.name, isValidName(data.name))

    /**
     * Checks if name is valid
     */
    private fun isValidName(name: String) = name.length > 2
}

/**
 * View-state for [NameFragment]
 */
data class NameViewState(val name: String, val nextEnabled: Boolean)