package com.example.omron.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omron.OmronApp
import com.example.omron.R
import com.example.omron.databinding.FragmentScannedDevicesBinding
import com.example.omron.di.ViewModelFactory
import com.example.omron.domain.implementation.DeviceViewModel
import com.example.omron.presentation.adapter.ScannedDeviceAdapter
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [ScannedDevicesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScannedDevicesFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: DeviceViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentScannedDevicesBinding? = null
    private val binding get() = _binding!!

    private var adapter = ScannedDeviceAdapter(ArrayList<OmronPeripheral>())

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
        binding.scannedDeviceList.adapter = this.adapter
        binding.scannedDeviceList.layoutManager = LinearLayoutManager(activity)
        binding.toggleScanButton.setOnClickListener{
            viewModel.toggleScan()
        }
        viewModel.scannedDevices.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            adapter.add(it)
        })
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