package com.example.coco.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coco.databinding.FragmentPriceChangeBinding
import com.example.coco.view.adapter.PriceListUpDownRVAdapter
import timber.log.Timber

class PriceChangeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentPriceChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentPriceChangeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllSelectedCoinData()

        viewModel.arr15min.observe(viewLifecycleOwner) {

            Timber.tag("15분").d(it.toString())

            val priceListRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price15m.adapter = priceListRVAdapter
            binding.price15m.layoutManager = LinearLayoutManager(requireContext())

        }

        viewModel.arr30min.observe(viewLifecycleOwner) {

            Timber.tag("30분").d(it.toString())

            val priceListRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price30m.adapter = priceListRVAdapter
            binding.price30m.layoutManager = LinearLayoutManager(requireContext())

        }

        viewModel.arr45min.observe(viewLifecycleOwner) {

            Timber.tag("45분").d(it.toString())

            val priceListRVAdapter = PriceListUpDownRVAdapter(requireContext(), it)
            binding.price45m.adapter = priceListRVAdapter
            binding.price45m.layoutManager = LinearLayoutManager(requireContext())

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}