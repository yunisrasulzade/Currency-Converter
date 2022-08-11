package com.example.currencyconverter.presentation.ui.activity

import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private lateinit var viewModel: MainViewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): MainViewModel {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        return viewModel
    }

    override fun inflateLayout(layoutInflater: LayoutInflater)  = ActivityMainBinding.inflate(layoutInflater)
}
