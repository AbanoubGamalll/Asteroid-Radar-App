package com.example.asteroidRadarApp.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidRadarApp.R
import com.example.asteroidRadarApp.adapter.AsteroidAdapter
import com.example.asteroidRadarApp.databinding.FragmentMainBinding
import com.example.asteroidRadarApp.model.AsteroidModel


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireContext())
        )[MainViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        binding.RecyclerView.adapter = AsteroidAdapter {
            findNavController().navigate(
                MainFragmentDirections.actionShowDetail(it)
            )
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.data_filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ShowToday -> viewModel.getAsteroidTodayList()
            R.id.ShowWeek -> viewModel.getAsteroidWeekList()
            else -> viewModel.getAsteroidAllList()
        }
        return true
    }

}