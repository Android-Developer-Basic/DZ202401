package ru.otus.dz_2024_01.wizard

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@ActivityRetainedScoped
class WizardCache @Inject constructor() {
    private val _state: MutableStateFlow<RegData> = MutableStateFlow(RegData())

    /**
     * Data state
     */
    val state: StateFlow<RegData> get() = _state.asStateFlow()

    /**
     * Sets name
     */
    fun setName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    /**
     * Sets address
     */
    fun setAddress(address: String) {
        _state.value = _state.value.copy(address = address)
    }
}

/**
 * Data for registration
 */
data class RegData(val name: String = "", val address: String = "")