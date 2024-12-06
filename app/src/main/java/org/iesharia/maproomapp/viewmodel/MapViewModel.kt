package org.iesharia.maproomapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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


    // Cargar marcadores y sus tipos
    init {
        loadMarkers()
        loadMarkerTypes()
    }

    private fun loadMarkers() {
        viewModelScope.launch(Dispatchers.IO) {
            val markerList = markerDao.getAllMarkers()
            _markers.value = markerList
        }
    }

    private fun loadMarkerTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            val types = markerTypeDao.getAllMarkerTypes()
            // Convertimos la lista de tipos de marcadores en un mapa con cada ID asociado a su nombre
            _markerTypes.value = types.associate { it.id to it.name }
        }
    }

    // Función para insertar nuevos marcadores
    private fun insertMarkers() {
        // Lista de marcadores a añadir
        val markers = listOf(
            Marker(
                name = "Ejemplo",
                latitude = "00",
                longitude = "00",
                markerTypeId = 1,
                description = "Una descripción"
            ),
        )
        CoroutineScope(Dispatchers.IO).launch {
            markers.forEach { marker ->
                markerDao.insert(marker)
            }
        }
    }
}