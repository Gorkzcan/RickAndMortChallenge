package com.gorkem.rmainviousg.model

data class ResultForDetail(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    var isSelected:Boolean = false,
    val residents: List<String>,
    val type: String,
    val url: String
)