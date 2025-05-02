package com.example.skycheck.data.mocks

import com.example.skycheck.data.model.entity.Location

object LocationMocks {
    fun mockCurrentLocation(): android.location.Location {
        return android.location.Location("mock").apply {
            latitude = 1.23
            longitude = 4.56
        }
    }

    fun mockSavedLocations(): List<Location> {
        return listOf(
            Location(
                id = 1,
                locality = "São Paulo",
                latitude = -23.5,
                longitude = -46.6,
                isCurrentUserLocality = false
            ),
            Location(
                id = 2,
                locality = "Rio de Janeiro",
                latitude = -22.9,
                longitude = -43.2,
                isCurrentUserLocality = false
            )
        )
    }

    fun mockLocation(): Location {
        return Location(
            id = 1,
            locality = "São Paulo",
            latitude = -23.5,
            longitude = -46.6,
            isCurrentUserLocality = false
        )
    }
}