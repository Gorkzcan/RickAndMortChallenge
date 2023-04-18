package com.gorkem.rmainviousg.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.media.browse.MediaBrowser.ItemCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gorkem.rmainviousg.R
import com.gorkem.rmainviousg.databinding.ItemCharBinding

import com.gorkem.rmainviousg.model.CharDetailModel
import kotlin.coroutines.CoroutineContext

class CharAdapter : RecyclerView.Adapter<CharAdapter.CharacterViewHolder>() {

    private lateinit var binding : ItemCharBinding
    private var characterList = mutableListOf<CharDetailModel>()
    private lateinit var context:Context
    private  var navDetail:((char:CharDetailModel) -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<CharDetailModel>) {
        characterList = list
        notifyDataSetChanged()
    }

     class CharacterViewHolder(val binding: ItemCharBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val charsList = characterList[position]
        val context = holder.itemView.context
        holder.binding.apply{
            charName.text = charsList.name
            Glide.with(context).load(charsList.image).into(charImage)
            charsList.gender?.let { getGenderImage(it) }?.let { charGender.setImageResource(it) }
        }
        holder.itemView.setOnClickListener{
            navDetail?.invoke(charsList)
        }
    }

    override fun getItemCount() = characterList.size

    fun navDetail(f: ((char: CharDetailModel) -> Unit)){
        navDetail = f
    }

    //daha rahat kod okunaklığı sağlaması açısından gender belirleme fonksiyonu yazıldı.
    private fun getGenderImage(gender: String): Int{
        return when(gender){
            "Male" -> R.drawable.male
            "Female" -> R.drawable.female
            else -> R.drawable.unknown
        }
    }
}
