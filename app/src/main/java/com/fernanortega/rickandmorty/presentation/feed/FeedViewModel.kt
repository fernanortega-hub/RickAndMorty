package com.fernanortega.rickandmorty.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.rickandmorty.domain.GetAllCharactersUseCase
import com.fernanortega.rickandmorty.domain.RefreshCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    getAllCharactersUseCase: GetAllCharactersUseCase,
    private val refreshCharactersUseCase: RefreshCharactersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        FeedUiState(
            isLoading = true
        )
    )
    val uiState: StateFlow<FeedUiState> =
        combine(_uiState, getAllCharactersUseCase()) { uiState, characters ->
            uiState.copy(
                characters = characters
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = _uiState.value
        )

    init {
        if (_uiState.value.characters.isEmpty()) {
            refreshCharacters()
        }
    }

    fun refreshCharacters() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                refreshCharactersUseCase()
                    .onFailure {
                        _uiState.update { it.copy(error = it.error) }
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false, error = null) }
            }
        }
    }
}