package com.gorkem.rmainviousg.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gorkem.rmainviousg.R
import com.gorkem.rmainviousg.adapter.CharAdapter
import com.gorkem.rmainviousg.adapter.LocationAdapter
import com.gorkem.rmainviousg.databinding.FragmentHomeBinding
import com.gorkem.rmainviousg.model.CharDetailModel
import com.gorkem.rmainviousg.model.Resource
import com.gorkem.rmainviousg.model.ResultForDetail
import com.gorkem.rmainviousg.util.hide
import com.gorkem.rmainviousg.util.show
import com.gorkem.rmainviousg.viewmodel.ApiVM
import kotlinx.coroutines.runBlocking
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val apiVM by viewModels<ApiVM>()
    /*
    "by viewModels" ifadesi, Android Jetpack kütüphanesinin bir parçası olan ViewModel kütüphanesi tarafından sağlanan bir kısayoldur.
    Bu kısayol, ViewModel'in doğru şekilde ömrünü yönetmesini sağlar
    ve aynı zamanda ViewModel'ın birden fazla parça tarafından paylaşılmasını da sağlar.
    Bu ifadeyi kullanarak ViewModel'i kendi kendine oluşturmak yerine,
     "by viewModels" ifadesiyle ViewModel'ı Activity veya Fragment içinde oluşturabilir
     ve ömrünü doğru şekilde yönetebilirsiniz.
     Ayrıca, ViewModel kütüphanesi tarafından sağlanan özellikleri kullanarak ViewModel'ın bir parçası olan
     LiveData'nın gözlemlemesini de kolaylaştırır.
     */
    private  var locAdapterList = ArrayList<ResultForDetail>()
    private var charAdapterList = ArrayList<CharDetailModel>()
    private var charIds = ArrayList<Int>()
    private var pageNumber = 1
    private lateinit var charAdapter : CharAdapter
    private lateinit var locAdapter : LocationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocations(pageNumber)
        setupLocationAdapter()
        setupCharacterAdapter()
        subscribeLocationsObserve()
        subscribeLocationObserve()
        subscribeCharactersById()
    }

    private fun subscribeCharactersById() {
        apiVM.multipleCharsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    binding.pbChar.hide()
                    charAdapterList.clear()
                    charAdapterList.addAll(it.result)
                    charAdapter.setData(charAdapterList)

                }
                else -> {

                }
            }
        }
    }


    private fun subscribeLocationsObserve() {
        apiVM.locationsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    locAdapterList.clear()
                    locAdapterList.addAll(it.result.results)
                    locAdapter.setData(locAdapterList)
                    binding.pbLoc.hide()
                    binding.rwLocList.show()
                }
                else -> {
                }
            }
        }
    }


    private fun getLocations(pageNumber: Int) {
        binding.pbLoc.show()
        binding.rwLocList.hide()

        runBlocking {
            apiVM.getLocations(pageNumber)
        }
    }

    private fun setupCharacterAdapter() {
        binding.rwChar.layoutManager = LinearLayoutManager(requireContext())
        charAdapter = CharAdapter()
        binding.rwChar.adapter = charAdapter
        charAdapter.navDetail {
            val action = HomeFragmentDirections.actionHomeFragmentToCharDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun subscribeLocationObserve() {
        apiVM.locationByIdLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    for (residentLink in it.result.residents) {
                        val characterId = residentLink.split("/").last().toInt()
                        charIds.add(characterId)
                    }

                    runBlocking {
                        apiVM.getMultipleChars(charIds)
                        charIds.clear()
                    }

                }
                else -> {
                }
            }
        }
    }


    private fun setupLocationAdapter() {
        binding.rwLocList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        locAdapter = LocationAdapter()
        binding.rwLocList.adapter = locAdapter
        locationAdapterOnClickListener()
        locationAdapterScrollListener(binding.rwLocList)
    }
    private fun locationAdapterOnClickListener() {
        locAdapter.setLocationIdListener {
            binding.pbChar.show()
            runBlocking {
                apiVM.getLocationById(it)
            }
        }

        locAdapter.setSelectedLocationListener { position ->
            for (i in 0 until locAdapterList.size) {
                locAdapterList[i].isSelected = i == position
            }
            locAdapter.setData(locAdapterList)
        }
    }


    private fun locationAdapterScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {

                    }
                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> {

                    }
                    else -> {
                        if (!recyclerView.canScrollHorizontally(1)) {
                            if (pageNumber == 7) {
                                Toast.makeText(
                                    requireContext(),
                                    "Son Sayfadasınız",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                pageNumber++

                                getLocations(pageNumber)
                                recyclerView.layoutManager?.scrollToPosition(0)
                            }
                        } else if (!recyclerView.canScrollHorizontally(-1)) {
                            if (pageNumber == 1) {
                                recyclerView.isNestedScrollingEnabled = false
                                Toast.makeText(
                                    requireContext(),
                                    "$pageNumber. Sayfaya Ulaştınız",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                pageNumber--
                                getLocations(pageNumber)
                            }

                        }
                    }
                }
            }


        })
    }


}