package com.gorkem.rmainviousg.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.gorkem.rmainviousg.model.CharDetailModel
import com.gorkem.rmainviousg.model.Location
import com.gorkem.rmainviousg.model.Origin
import com.gorkem.rmainviousg.databinding.FragmentCharDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CharDetailFragment : Fragment() {
    private lateinit var binding: FragmentCharDetailBinding
    private lateinit var charDetailModel : CharDetailModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processArgument()
        setView()
        onClickProcess()
    }


    private fun onClickProcess() {
        binding.apply {
            arrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setView() {
        val origin = Origin(charDetailModel.origin?.name).name
        val location = Location(charDetailModel.location?.name).name
        val episodes = arrayListOf<String>()
        for (episode in charDetailModel.episode) {
            episodes.add(episode.split("/").last())
        }
        val date = charDetailModel.created?.let { modifyDateLayout(it) }

        binding.apply {
            charStatusDesc.text = charDetailModel.status
            charNametw.text = charDetailModel.name
            charSpecyDesc.text = charDetailModel.species
            charGenderDesc.text = charDetailModel.gender
            charOriginDesc.text = origin
            charLocationDesc.text = location
            charEpisodesDesc.text = episodes.toString().replace("[","").replace("]","")
            charCreatedDesc.text = "${date?.get(0)} , ${date?.get(1)}"
            Glide.with(requireContext()).load(charDetailModel.image).into(charImage)
        }
    }


    private fun processArgument() {
        val argsModel = arguments?.let { CharDetailFragmentArgs.fromBundle(it).charDetailModel }
        charDetailModel = argsModel ?: CharDetailModel()
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(ParseException::class)
    private fun modifyDateLayout(date: String): List<String> {

        val allDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date)
        val parsedDate = allDate.let { SimpleDateFormat("dd.MMM.yyyy HH:mm:ss").format(it) }
        return parsedDate.split(" ")
    }


}