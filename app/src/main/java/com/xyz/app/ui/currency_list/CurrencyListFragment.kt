package com.xyz.app.ui.currency_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xyz.app.data.vo.Status
import com.xyz.app.databinding.FragmentCurrencyListBinding
import com.xyz.app.domain.CurrencyInfo

private const val TAG = "CurrencyListFragment"

class CurrencyListFragment : Fragment() {
    private val viewModel: CurrencyListViewModel by activityViewModels()
    private lateinit var binding: FragmentCurrencyListBinding
    private lateinit var currencyListAdapter: CurrencyListAdapter

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
        observeCurrencyInfo()
    }

    private fun observeCurrencyInfo() {
        viewModel.currencyInfos.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    fadeOut()
                }
                else -> {
                    fadeIn()
                }
            }
        }
    }

    private fun fadeIn() {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator() //add this
        fadeIn.duration = 500
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                Log.d(TAG, "onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.currencyListRecyclerView.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {
                Log.d(TAG, "onAnimationRepeat")
            }
        })
        binding.currencyListRecyclerView.startAnimation(fadeIn)
    }

    private fun fadeOut() {
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.startOffset = 0
        fadeOut.duration = 500
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                Log.d(TAG, "onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.currencyListRecyclerView.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {
                Log.d(TAG, "onAnimationRepeat")
            }
        })
        binding.currencyListRecyclerView.startAnimation(fadeOut)
    }

    override fun onDestroyView() {
        binding.currencyListRecyclerView.adapter = null
        super.onDestroyView()
    }

    fun updateCurrencyList(currencyList: List<CurrencyInfo>) {
        fadeOut()
        currencyListAdapter.submitList(currencyList)
    }

    private fun initUi() {
        binding.currencyListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.currencyListRecyclerView.setHasFixedSize(true)

        currencyListAdapter = CurrencyListAdapter(viewModel)
        binding.currencyListRecyclerView.adapter = currencyListAdapter
    }

}