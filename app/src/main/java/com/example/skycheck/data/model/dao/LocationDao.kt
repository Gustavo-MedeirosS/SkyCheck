package com.example.skycheck.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.skycheck.data.model.entity.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert
    suspend fun saveLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)

    @Query("SELECT * FROM location")
    suspend fun getLocations(): List<Location>
}