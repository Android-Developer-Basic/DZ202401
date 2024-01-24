package ru.otus.dz_2024_01

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.otus.dz_2024_01.wizard.AddressViewState
import ru.otus.dz_2024_01.wizard.WizardCache
import javax.inject.Inject

/**
 * View-model for [DataFragment]
 */
@HiltViewModel
class DataViewModel @Inject constructor(private val cache: WizardCache) : ViewModel() {
    private val _viewState = MutableStateFlow(render())

    /**
     * View-state for [DataFragment]
     */
    val viewState: StateFlow<DataViewState> get() = _viewState.asStateFlow()

    /**
     * Renders view-state
     */
    private fun render() = DataViewState(cache.name, cache.address)
}

/**
 * View-state for [DataFragment]
 */
data class DataViewState(val name: String, val address: String)