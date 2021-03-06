package com.xyz.app

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.xyz.app.data.db.visible
import com.xyz.app.data.vo.Status
import com.xyz.app.databinding.ActivityMainBinding
import com.xyz.app.domain.CurrencyInfo
import com.xyz.app.ui.currency_list.CurrencyListFragment
import com.xyz.app.ui.currency_list.CurrencyListViewModel

private const val TAG = "MainActivity"

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
            binding.welcomeTextView.visibility = GONE
            updateCurrencyList(emptyList())
            viewModel.fetchCurrencyInfo()
        }

        binding.sortCurrencyListButton.setOnClickListener {
            updateCurrencyList(emptyList())
            viewModel.sortCurrencyInfo()
        }
    }

    private fun updateCurrencyList(list: List<CurrencyInfo>) {
        (supportFragmentManager.findFragmentById(R.id.container) as? CurrencyListFragment)?.updateCurrencyList(
            list
        )
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
            binding.sortCurrencyListButton.isEnabled = resource.status != Status.LOADING
            binding.fetchCurrencyListButton.isEnabled = resource.status != Status.LOADING
            binding.loadingProgress.visible(resource.status == Status.LOADING)

            when (resource.status) {
                Status.SUCCESS -> {
                    updateCurrencyList(resource.data ?: emptyList())
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
                else -> {
                    Log.d(TAG, "loading currency info")
                }
            }
        }
    }
}