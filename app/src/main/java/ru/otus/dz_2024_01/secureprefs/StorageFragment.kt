package ru.otus.dz_2024_01.secureprefs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.otus.dz_2024_01.databinding.FragmentStorageBinding
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class StorageFragment : Fragment() {
    private var binding: FragmentStorageBinding? = null
    private inline fun withBinding(block: FragmentStorageBinding.() -> Unit) {
        binding?.block()
    }

    @Inject
    @Named("commonValues")
    lateinit var commonStorage: KeyValueStorage

    @Inject
    @Named("secureValues")
    lateinit var secureStorage: KeyValueStorage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStorageBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding {
            saveCommon.setOnClickListener {
                commonStorage.put("value", value.text.toString())
            }
            saveSecure.setOnClickListener {
                secureStorage.put("value", value.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}