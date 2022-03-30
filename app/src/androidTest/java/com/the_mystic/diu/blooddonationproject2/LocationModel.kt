package com.the_mystic.diu.blooddonationproject2
data class LocationModel(
    val bn_name: String?,
    val district_id: String?,
    val id: String?,
    val name: String?,
    val url: String?
) {
    override fun toString(): String {
        return name.toString()
    }
}
