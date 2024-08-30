package com.fernanortega.rickandmorty.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fernanortega.rickandmorty.domain.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    getAllCharactersUseCase: GetAllCharactersUseCase,
) : ViewModel() {
    /**
     * Flow de [PagingData] de [Character] que se obtiene de [GetAllCharactersUseCase],
     * Se usa cachedIn para evitar memory leaks y posible crash
     * El stateIn se usa para siempre tener un flow de datos corriendo hasta 5 segundos después de su
     * último collector (en este caso las vistas de Feed)
     */
    val charactersPaging = getAllCharactersUseCase()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )
}