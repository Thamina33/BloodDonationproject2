package com.rahat.gtaf.blooddonationproject2.model

data class ProfileModel(
    val name: String = "",
    val uid: String = "",
    val mail: String = "",
    val phone: String = "",
    val address: String = "",
    val bg: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val isDonate: Boolean = false,
)

