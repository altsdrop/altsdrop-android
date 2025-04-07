package com.altsdrop.feature.airdrop.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.feature.airdrop.domain.usecase.GetExploreAirdropsUseCase
import com.altsdrop.feature.airdrop.domain.usecase.GetFeaturedAirdropsUseCase
import com.altsdrop.feature.airdrop.domain.usecase.GetHighlyRatedAirdropsUseCase
import com.altsdrop.feature.airdrop.domain.usecase.GetNewAirdropsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirdropHomeViewModel @Inject constructor(
    private val getFeaturedAirdropsUseCase: GetFeaturedAirdropsUseCase,
    private val getNewAirdropsUseCase: GetNewAirdropsUseCase,
    private val getExploreAirdropsUseCase: GetExploreAirdropsUseCase,
    private val getHighlyRatedAirdropsUseCase: GetHighlyRatedAirdropsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AirdropHomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getFeaturedAirdrops()
    }

    private fun getFeaturedAirdrops() {
        viewModelScope.launch {
            val featuredAirdrops = getFeaturedAirdropsUseCase()

            _uiState.update {
                it.copy(featuredAirdrops = featuredAirdrops)
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }

        when (index) {
            0 -> getNewAirdrops()
            1 -> getHighlyRatedAirdrops()
            2 -> getExploreAirdrops()
        }
    }

    private fun getNewAirdrops() {
        viewModelScope.launch {
            val newAirdrops = getNewAirdropsUseCase()

            _uiState.update { it.copy(newAirdrops = newAirdrops) }
        }
    }

    private fun getHighlyRatedAirdrops() {
        viewModelScope.launch {
            val highlyRatedAirdrops = getHighlyRatedAirdropsUseCase()

            _uiState.update { it.copy(highlyRatedAirdrops = highlyRatedAirdrops) }
        }
    }

    private fun getExploreAirdrops() {
        viewModelScope.launch {
            val exploreAirdrops = getExploreAirdropsUseCase()

            _uiState.update { it.copy(exploreAirdrops = exploreAirdrops) }
        }
    }
}