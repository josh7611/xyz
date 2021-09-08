package com.xyz.app.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xyz.app.R
import com.xyz.app.databinding.FragmentCurrencyListBinding
import com.xyz.app.domain.CurrencyInfo

class CurrencyListFragment : Fragment() {
    private val viewModel: CurrencyListViewModel by activityViewModels()
    private lateinit var binding: FragmentCurrencyListBinding
    private val currencyListAdapter = CurrencyListAdapter()

    companion object {
        fun newInstance() = CurrencyListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun onDestroyView() {
        binding.currencyListRecyclerView.adapter = null
        super.onDestroyView()
    }

    fun updateCurrencyList(currencyList: List<CurrencyInfo>) {
        currencyListAdapter.submitList(currencyList)
    }

    private fun initUi() {
        binding.currencyListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.currencyListRecyclerView.setHasFixedSize(true)
        binding.currencyListRecyclerView.adapter = currencyListAdapter
    }

}