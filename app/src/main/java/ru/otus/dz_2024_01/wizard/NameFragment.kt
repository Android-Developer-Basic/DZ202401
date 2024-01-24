package ru.otus.dz_2024_01.wizard

import android.os.Bundle
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
import ru.otus.dz_2024_01.databinding.FragmentNameBinding

@AndroidEntryPoint
class NameFragment : Fragment() {
    private var binding: FragmentNameBinding? = null
    private inline fun withBinding(block: FragmentNameBinding.() -> Unit) {
        binding?.block()
    }

    private val viewModel: NameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding {
            next.setOnClickListener {
                findNavController().navigate(R.id.action_nameFragment_to_addressFragment)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.viewState.collect {
                        name.setTextKeepState(it.name)
                        next.isEnabled = it.nextEnabled
                    }
                }
            }

            name.addTextChangedListener {
                viewModel.setName(it.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}