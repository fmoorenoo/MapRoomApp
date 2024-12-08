package org.iesharia.maproomapp.model

import androidx.room.*

@Dao interface MarkerDao {
    // Añadir marcadores
    @Insert
    suspend fun insert(marker: Marker)

    // Borrar marcadores
    @Delete
    suspend fun delete(marker: Marker?)

    // Obtener todos los marcadores
    @Query("SELECT * FROM markers")
    suspend fun getAllMarkers(): List<Marker>

    // Obtener un marcador por su ID
    @Query("SELECT * FROM markers WHERE id = :id")
    fun getMarkerById(id: Int): Marker?
}