package ru.otus.dz_2024_01

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.otus.dz_2024_01.wizard.RegData
import ru.otus.dz_2024_01.wizard.WizardCache
import javax.inject.Inject

/**
 * View-model for [DataFragment]
 */
@HiltViewModel
class DataViewModel @Inject constructor(private val cache: WizardCache) : ViewModel() {
    /**
     * View-state for [DataFragment]
     */
    val viewState: StateFlow<DataViewState> get() = cache.state
        .map { render(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, render(cache.state.value))

    /**
     * Renders view-state
     */
    private fun render(data: RegData) = DataViewState(data.name, data.address)
}

/**
 * View-state for [DataFragment]
 */
data class DataViewState(val name: String, val address: String)