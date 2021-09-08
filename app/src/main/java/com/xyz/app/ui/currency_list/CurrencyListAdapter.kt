package com.xyz.app.ui.currency_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xyz.app.databinding.ListitemCurrencyInfoBinding
import com.xyz.app.domain.CurrencyInfo
import com.xyz.app.ui.common.firstLetterAsUpperCase


class CurrencyListAdapter(private val viewmodel: CurrencyListViewModel) :
    ListAdapter<CurrencyInfo, CurrencyListAdapter.CurrencyListViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<CurrencyInfo>() {
        override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyListViewHolder {
        val itemBinding =
            ListitemCurrencyInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CurrencyListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class CurrencyListViewHolder(private val binding: ListitemCurrencyInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val currencyInfo = getItem(position)
            binding.iconTextView.text = currencyInfo.name.firstLetterAsUpperCase()
            binding.currencyNameTextView.text = currencyInfo.name
            binding.symbolTextView.text = currencyInfo.symbol

            binding.root.setOnClickListener {
                viewmodel.selectCurrencyInfo(currencyInfo)
            }
        }
    }
}
