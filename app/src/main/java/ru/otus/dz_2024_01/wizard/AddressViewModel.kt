package ru.otus.dz_2024_01.wizard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
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

    private val entry = MutableSharedFlow<String>()

    /**
     * View-state for [AddressFragment]
     */
    @OptIn(FlowPreview::class)
    val viewState: StateFlow<AddressViewState> get() =
        combine(
            cache.state,
            entry.debounce(1L.seconds).map { service.suggest(it) }
        ) { data, addresses ->
            render(data, addresses)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            render(cache.state.value, emptyList())
        )

    /**
     * Sets address
     */
    fun setAddress(address: String) {
        cache.setAddress(address)
    }

    /**
     * Searches for address
     */
    fun searchAddress(address: String) {
        setAddress(address)
        viewModelScope.launch {
            entry.emit(address)
        }
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
