package com.fernanortega.rickandmorty.common

class BackendException(
    status: Int
): Exception() {
    override val message: String = "Request failed with $status code"
}