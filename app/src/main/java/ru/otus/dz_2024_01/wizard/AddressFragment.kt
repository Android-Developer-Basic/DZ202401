package ru.otus.dz_2024_01.wizard

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.otus.dz_2024_01.R
import ru.otus.dz_2024_01.databinding.FragmentAddressBinding

@AndroidEntryPoint
class AddressFragment : Fragment() {
    private var binding: FragmentAddressBinding? = null
    private inline fun withBinding(block: FragmentAddressBinding.() -> Unit) {
        binding?.block()
    }

    private val viewModel: AddressViewModel by viewModels()

    private lateinit var textWatcher: TextWatcher

    private val adapter = AddressAdapter {
        viewModel.setAddress(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding {
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            storage.setOnClickListener {
                findNavController().navigate(R.id.action_addressFragment_to_storageFragment)
            }

            textWatcher = address.addTextChangedListener {
                viewModel.searchAddress(it.toString())
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewState.collect {

                        address.removeTextChangedListener(textWatcher)
                        address.setTextKeepState(it.address)
                        address.addTextChangedListener(textWatcher)

                        adapter.submitList(it.addressList)
                    }
                }
            }

            recycler.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}