package com.example.coco.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coco.databinding.FragmentCoinListBinding
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.view.adapter.CoinListRVAdapter
import com.example.coco.view.adapter.PriceListUpDownRVAdapter
import timber.log.Timber

class CoinListFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!

    private val selectedList = ArrayList<InterestCoinEntity>()
    private val unSelectedList = ArrayList<InterestCoinEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllInterestCoinData()

        viewModel.selectedCoinList.observe(viewLifecycleOwner) {

            selectedList.clear()
            unSelectedList.clear()

            for (item in it) {

                if (item.selected) {
                    selectedList.add(item)
                } else {
                    unSelectedList.add(item)
                }

            }

            setSelectedListRV()

        }

    }

    private fun setSelectedListRV() {

        val selectedRVAdapter = CoinListRVAdapter(requireContext(), selectedList)
        binding.selectedCoinRV.adapter = selectedRVAdapter
        binding.selectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        selectedRVAdapter.itemClick = object : CoinListRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                viewModel.updateInterestCoinData(selectedList[position])

            }
        }

        val unSelectedRVAdapter = CoinListRVAdapter(requireContext(), unSelectedList)
        binding.unSelectedCoinRV.adapter = unSelectedRVAdapter
        binding.unSelectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        unSelectedRVAdapter.itemClick = object : CoinListRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                viewModel.updateInterestCoinData(unSelectedList[position])

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}