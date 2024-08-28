package com.fernanortega.rickandmorty.data.network.services.character.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPaginationInfo(
    @SerialName("next") val next: String?,
    @SerialName("prev") val prev: String?
)
