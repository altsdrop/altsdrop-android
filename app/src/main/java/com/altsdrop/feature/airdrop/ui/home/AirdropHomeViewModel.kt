package com.altsdrop.feature.airdrop.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.altsdrop.feature.airdrop.domain.usecase.GetFeaturedAirdropsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirdropHomeViewModel @Inject constructor(
    private val getFeaturedAirdropsUseCase: GetFeaturedAirdropsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(AirdropHomeScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getFeaturedAirdrops()
        getNewAirdrops()
    }

    private fun getFeaturedAirdrops() {
        viewModelScope.launch {
            val featuredAirdrops = getFeaturedAirdropsUseCase()

            _uiState.update {
                it.copy(
                    featuredAirdrops = featuredAirdrops
                )
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update {
            it.copy(
                selectedTabIndex = index
            )
        }

        when (index) {
            0 -> getNewAirdrops()
            1 -> getHighlyRatedAirdrops()
            2 -> getExploreAirdrops()
        }
    }

    private fun getNewAirdrops() {
        viewModelScope.launch {
            val featuredAirdrops: ArrayList<Airdrop> = arrayListOf()
            featuredAirdrops.addAll(getFeaturedAirdropsUseCase())

            _uiState.update {
                it.copy(
                    newAirdrops = featuredAirdrops
                )
            }
        }
    }

    private fun getHighlyRatedAirdrops() {
        viewModelScope.launch {
            val featuredAirdrops = getFeaturedAirdropsUseCase()

            _uiState.update {
                it.copy(
                    highlyRatedAirdrops = featuredAirdrops
                )
            }
        }
    }

    private fun getExploreAirdrops() {
        viewModelScope.launch {
            val featuredAirdrops = getFeaturedAirdropsUseCase()

            _uiState.update {
                it.copy(
                    exploreAirdrops = featuredAirdrops
                )
            }
        }
    }

}