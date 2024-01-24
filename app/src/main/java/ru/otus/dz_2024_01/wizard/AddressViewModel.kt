package ru.otus.dz_2024_01.wizard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.otus.domain.data.Address
import ru.otus.domain.net.AddressSuggestService
import ru.otus.net.AddressSuggestServiceImpl
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

/**
 * View-model for [AddressFragment]
 */
@HiltViewModel
class AddressViewModel @Inject constructor(
    private val cache: WizardCache,
    private val service: AddressSuggestService
) : ViewModel() {

    private val addresses = MutableStateFlow<List<Address>>(emptyList())
    private var addressesJob: Job? = null

    /**
     * View-state for [AddressFragment]
     */
    val viewState: StateFlow<AddressViewState> get() =
        combine(cache.state, addresses) { data, addresses ->
            render(data, addresses)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            render(cache.state.value, addresses.value)
        )

    /**
     * Sets address
     */
    fun setAddress(address: String) {
        cache.setAddress(address)
        cancel()
        addresses.value = emptyList()
    }

    /**
     * Searches for address
     */
    fun searchAddress(address: String) {
        cache.setAddress(address)
        cancel()
        addressesJob = viewModelScope.launch {
            delay(1L.seconds)
            addresses.value = service.suggest(address)
        }
    }

    private fun cancel() {
        addressesJob?.cancel()
    }

    /**
     * Renders view-state
     */
    private fun render(data: RegData, addresses: List<Address>) = AddressViewState(data.address, addresses)
}

/**
 * View-state for [AddressFragment]
 */
data class AddressViewState(val address: String, val addressList: List<Address>)

@Module
@InstallIn(ViewModelComponent::class)
interface AddressViewModelModule {
    @Binds
    @ViewModelScoped
    fun service(impl: AddressSuggestServiceImpl): AddressSuggestService
}
