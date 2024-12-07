package org.iesharia.maproomapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.iesharia.maproomapp.model.Marker
import org.iesharia.maproomapp.model.MarkerDao
import org.iesharia.maproomapp.model.MarkerTypeDao

class MapViewModel(
    private val markerDao: MarkerDao,
    private val markerTypeDao: MarkerTypeDao
) : ViewModel() {
    // Almacenar los marcadores
    // Lista mutable y privada con los marcadores
    private val _markers = MutableStateFlow<List<Marker>>(emptyList())

    /* Misma lista pero:
        - pública, para poder ser observada por los composables
        - inmutable para que no sea modificada desde fuera del viewmodel */
    val markers: StateFlow<List<Marker>> = _markers

    // Almacenar los tipos de marcador (guardando el ID con el nombre que corresponde)
    // Misma lógica que para cargar marcadores
    private val _markerTypes = MutableStateFlow<Map<Int, String>>(emptyMap())
    val markerTypes: StateFlow<Map<Int, String>> = _markerTypes
}