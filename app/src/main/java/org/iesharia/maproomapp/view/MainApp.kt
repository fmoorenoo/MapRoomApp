package org.iesharia.maproomapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.iesharia.maproomapp.model.MapDatabase
import org.iesharia.maproomapp.model.Marker

@Composable
fun MainApp(database: MapDatabase, markers: List<Marker>) {
    LoadMap(markers)
}