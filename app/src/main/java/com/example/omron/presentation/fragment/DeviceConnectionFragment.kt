package com.example.omron.presentation.fragment

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.omron.OmronApp
import com.example.omron.R
import com.example.omron.databinding.FragmentDeviceConnectionBinding
import com.example.omron.di.ViewModelFactory
import com.example.omron.domain.implementation.ConnectionViewModel
import com.example.omron.utils.fragment.makeSnack
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [DeviceConnectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeviceConnectionFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val connectionViewModel: ConnectionViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentDeviceConnectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as OmronApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeviceConnectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectionViewModel.createBond()
        connectionViewModel.currentDevice.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            makeSnack("???????????????????? ${it.modelName} ?????????????? ????????????????????")
            findNavController()
                .navigate(R.id.action_deviceConnectionFragment_to_controlDeviceFragment)
        })
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
         * @return A new instance of fragment DeviceConnectionFragment.
         */
        @JvmStatic
        fun newInstance() = DeviceConnectionFragment()
    }
}