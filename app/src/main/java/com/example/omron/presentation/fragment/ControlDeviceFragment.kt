package com.example.omron.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.omron.OmronApp
import com.example.omron.R
import com.example.omron.databinding.FragmentControlDeviceBinding
import com.example.omron.di.ViewModelFactory
import com.example.omron.domain.implementation.ConnectionViewModel
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [ControlDeviceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ControlDeviceFragment : Fragment() {
    private var _binding: FragmentControlDeviceBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val connectionViewModel: ConnectionViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as OmronApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentControlDeviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment ControlDeviceFragment.
         */
        @JvmStatic
        fun newInstance() = ControlDeviceFragment()
    }
}