package com.gorkem.rmainviousg.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class CharDetailModel(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("species")
    var species: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("origin")
    var origin: @RawValue Origin? = Origin(),
    @SerializedName("location")
    var location: @RawValue Location? = Location(),
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("episode")
    var episode: ArrayList<String> = arrayListOf(),
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("created")
    var created:String?=null

) : Parcelable

