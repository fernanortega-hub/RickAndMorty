package com.fernanortega.rickandmorty.presentation.characterdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fernanortega.rickandmorty.domain.GetCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
): ViewModel() {
    private val characterId: Int? = savedStateHandle[CHARACTER_ID_SAVED_STATE_KEY]
    private val _uiState: MutableStateFlow<CharacterDetailsUiState> = MutableStateFlow(CharacterDetailsUiState())
    val uiState: StateFlow<CharacterDetailsUiState> = _uiState.asStateFlow()

    /**
     * Actualiza el id del personaje con el argumento pasado en la navegación y lo obtiene de la base de datos o desde la api
     */
    fun updateCharacterId(characterId: Int) {
        // Se guarda el id del personaje en el savedStateHandle para mantenerlo
        // en ciertos escenarios donde la app pueda ser detenida forzadamente por el sistema
        savedStateHandle[CHARACTER_ID_SAVED_STATE_KEY] = characterId
        getCharacterById()
    }

    private fun getCharacterById() {
        if (characterId == null) return
        viewModelScope.launch {
            getCharacterByIdUseCase(characterId)
                .onFailure {
                    _uiState.update { state ->
                        state.copy(
                            error = it.message
                        )
                    }
                }
                .onSuccess {
                    _uiState.update { state -> state.copy(character = it) }
                }
        }
    }

}

private const val CHARACTER_ID_SAVED_STATE_KEY = "characterId"