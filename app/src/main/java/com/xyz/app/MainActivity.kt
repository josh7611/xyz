package com.xyz.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.xyz.app.data.vo.Status
import com.xyz.app.databinding.ActivityMainBinding
import com.xyz.app.ui.currency_list.CurrencyListFragment
import com.xyz.app.ui.currency_list.CurrencyListViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: CurrencyListViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CurrencyListFragment.newInstance())
            .commitNow()

        initUi()
        observeCurrencyInfo()
        observeSelectedCurrencyInfo()
    }

    private fun initUi() {
        binding.fetchCurrencyListButton.setOnClickListener {
            viewModel.fetchCurrencyInfo()
        }

        binding.sortCurrencyListButton.setOnClickListener {
            viewModel.sortCurrencyInfo()
        }
    }

    private fun observeSelectedCurrencyInfo() {
        viewModel.selectedCurrencyInfo.observe(this) { currencyInfo ->
            toast?.cancel()
            toast = Toast.makeText(
                this,
            "${currencyInfo.name} selected!!",
                Toast.LENGTH_LONG
            ).also { it.show() }
        }
    }

    private fun observeCurrencyInfo() {
        viewModel.currencyInfos.observe(this) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    (supportFragmentManager.findFragmentById(R.id.container) as? CurrencyListFragment)?.updateCurrencyList(
                        resource.data ?: emptyList()
                    )
                }
                Status.ERROR -> {
                    toast?.cancel()
                    toast = Toast.makeText(
                        this,
                        getString(
                            resource.exception?.defaultDisplayedStringResId
                                ?: R.string.global_error_unknown
                        ),
                        Toast.LENGTH_LONG
                    ).also { it.show() }
                }
                Status.LOADING -> {

                }
            }
        }

    }

}