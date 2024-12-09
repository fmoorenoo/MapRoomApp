package org.iesharia.maproomapp.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "markers",
    // Fuente: https://stackoverflow.com/questions/47511750/how-to-use-foreign-key-in-room-persistence-library
    foreignKeys = [
        ForeignKey(
            entity = MarkerType::class,
            // Referencia en la tabla MarkerType
            parentColumns = ["id"],
            // Referencia en esta tabla
            childColumns = ["markerTypeId"],
        )
    ]
)

data class Marker(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    // Coordenadas geográficas
    val latitude: String,
    val longitude: String,
    val description: String? = null,
    val markerTypeId: Int
)
