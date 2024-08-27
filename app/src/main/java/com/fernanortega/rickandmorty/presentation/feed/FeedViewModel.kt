package com.fernanortega.rickandmorty.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.rickandmorty.domain.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    getAllCharactersUseCase: GetAllCharactersUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<FeedUiState> = MutableStateFlow(FeedUiState(emptyList()))
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    characters = getAllCharactersUseCase()
                )
            }
        }
    }
}