package org.iesharia.maproomapp.model.dao

import androidx.room.*
import org.iesharia.maproomapp.model.entities.MarkerType

@Dao interface MarkerTypeDao {
    // Añadir tipo de marcador
    @Insert
    suspend fun insertMarkerType(markerType: MarkerType)

    // Obtener todos los tipos de marcadores
    @Query("SELECT * FROM marker_types")
    suspend fun getAllMarkerTypes(): List<MarkerType>

    // Obtener un tipo de marcador según su ID
    @Query("SELECT * FROM marker_types WHERE id = :id")
    fun getMarkTypeById(id: Int): MarkerType?
}