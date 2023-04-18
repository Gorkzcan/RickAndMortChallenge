package com.gorkem.rmainviousg.adapter


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.gorkem.rmainviousg.databinding.ItemLocationBinding
import com.gorkem.rmainviousg.model.Location
import com.gorkem.rmainviousg.model.ResultForDetail

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private lateinit var locationIdListener: ((locationId: Int)-> Unit)
    private lateinit var selectedLocationListener : ((position: Int)-> Unit)
    private var locationList = mutableListOf<ResultForDetail>()
    //performansın daha iyi olması ve ekleme çıkarma işlemi için
    //mutableListOf kullanımı ArrayList yerine daha iyi olur.

   //genellikle notifyDataSetChanged() çağrısı kullanılır ancak bu veri kümesi değişince tüm öğeleri yeniden yükler.
    //performans iyileştirmesi açısından notifyItemRangeChanged kullanmak daha iyidir.

    fun setData(list: MutableList<ResultForDetail>) {
        locationList.clear()
        locationList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

    class LocationViewHolder(val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val locList = locationList[position]
        with(holder.binding){
            twLocationName.text = locList.name
            changeSelectedLocationStyle(position, twLocationName)
            cwLocRow.setOnClickListener {
                locationIdListener.invoke(locList.id)
                selectedLocationListener.invoke(position)
            }
        }
    }

    override fun getItemCount() = locationList.size

    fun setLocationIdListener(listener: ((locationId: Int)-> Unit)){
        locationIdListener = listener
    }

    fun setSelectedLocationListener(listener: ((position : Int)-> Unit)){
        selectedLocationListener = listener
    }

    private fun changeSelectedLocationStyle(position: Int, locText: TextView) {
        if (locationList[position].isSelected) {
            locText.setTextColor(Color.parseColor("#ffffff"))
        } else {
            locText.setTextColor(Color.parseColor("#000000"))
        }
    }

}
