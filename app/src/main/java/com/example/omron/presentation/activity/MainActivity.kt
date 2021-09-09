package com.example.omron.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.omron.OmronApp
import com.example.omron.databinding.ActivityMainBinding
import com.example.omron.di.ViewModelFactory
import com.example.omron.domain.implementation.DeviceViewModel
import com.example.omron.utils.activity.makeToast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val deviceViewModel: DeviceViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as OmronApp).appComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
