package org.iesharia.maproomapp.model

import androidx.room.*

@Dao interface MarkerDao {
    // Añadir marcadores
    @Insert
    suspend fun insert(marker: Marker)

    // Obtener todos los marcadores
    @Query("SELECT * FROM markers")
    suspend fun getAllMarkers(): List<Marker>
}