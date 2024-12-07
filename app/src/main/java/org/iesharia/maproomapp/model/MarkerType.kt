package org.iesharia.maproomapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marker_types")
data class MarkerType(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)