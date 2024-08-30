package com.fernanortega.rickandmorty.data.repository

import com.fernanortega.rickandmorty.domain.model.SearchContent

interface SearchContentRepository {
    /**
     * Obtiene los resultados (personajes) a partir de su [query], retornandolos en un [Result]
     * para el mejor manejo de los errores al momento de no encontrar resultados o si.
     */
    suspend fun getSearchContents(query: String): Result<SearchContent>
}