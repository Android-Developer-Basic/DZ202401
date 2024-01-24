package ru.otus.dz_2024_01.wizard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * View-model for [NameFragment]
 */
@HiltViewModel
class NameViewModel @Inject constructor(private val cache: WizardCache) : ViewModel() {

    private val _viewState = MutableStateFlow(render())

    /**
     * View-state for [NameFragment]
     */
    val viewState: StateFlow<NameViewState> get() = _viewState.asStateFlow()

    /**
     * Sets name
     */
    fun setName(name: String) {
        cache.name = name
        _viewState.value = render()
    }

    /**
     * Renders view-state
     */
    private fun render() = NameViewState(cache.name, isValidName(cache.name))

    /**
     * Checks if name is valid
     */
    private fun isValidName(name: String) = name.length > 2
}

/**
 * View-state for [NameFragment]
 */
data class NameViewState(val name: String, val nextEnabled: Boolean)