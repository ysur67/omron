package com.example.omron.presentation.fragment

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omron.OmronApp
import com.example.omron.R
import com.example.omron.databinding.FragmentScannedDevicesBinding
import com.example.omron.di.ViewModelFactory
import com.example.omron.domain.implementation.ConnectionViewModel
import com.example.omron.domain.implementation.ScanViewModel
import com.example.omron.presentation.adapter.ScannedDeviceAdapter
import com.example.omron.utils.Const
import com.vmadalin.easypermissions.EasyPermissions
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [ScannedDevicesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScannedDevicesFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ScanViewModel by activityViewModels { viewModelFactory }
    private val connectionViewModel: ConnectionViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentScannedDevicesBinding? = null
    private val binding get() = _binding!!

    private var adapter = ScannedDeviceAdapter(ArrayList())

    private val hasPermissions: Boolean
        get() = EasyPermissions.hasPermissions(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_PRIVILEGED,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.INTERNET
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as OmronApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannedDevicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasPermissions) {
            requestRequiredPermissions()
        }
        adapter.onItemClick = {
            connectionViewModel.connect(it)
            viewModel.toggleScan()
        }
        binding.scannedDeviceList.adapter = this.adapter
        binding.scannedDeviceList.layoutManager = LinearLayoutManager(activity)
        binding.toggleScanButton.setOnClickListener{
            viewModel.toggleScan()
        }
        viewModel.scannedDevices.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            adapter.add(it)
        })
        connectionViewModel.currentDevice.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController()
                .navigate(R.id.action_scannedDevicesFragment_to_controlDeviceFragment)
        })
    }

    private fun requestRequiredPermissions() {
        EasyPermissions.requestPermissions(
            host = this,
            rationale = getString(R.string.permissions_rationale),
            requestCode = Const.REQUEST_PERMISSIONS_CODE,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_PRIVILEGED,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment ScannedDevicesFragment.
         */
        @JvmStatic
        fun newInstance() = ScannedDevicesFragment()
    }
}