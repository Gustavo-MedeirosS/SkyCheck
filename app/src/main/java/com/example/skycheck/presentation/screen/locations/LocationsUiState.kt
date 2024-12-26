package com.example.skycheck.presentation.screen.locations

import com.example.skycheck.data.model.dto.GeocodeLocationDto
import com.example.skycheck.data.model.entity.Location

data class LocationsUiState(
    val userLocations: List<Location>? = null,
    val geocodeLocations: List<GeocodeLocationDto> = emptyList(),
    val isSearching: Boolean = false
)