package org.iesharia.maproomapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.iesharia.maproomapp.model.entities.*
import org.iesharia.maproomapp.model.dao.*
import kotlin.collections.forEach

class MapViewModel(
    private val markerDao: MarkerDao,
    private val markerTypeDao: MarkerTypeDao,
    private val favoriteDao: FavoriteDAO // DAO para gestionar favoritos
) : ViewModel() {
    // Almacenar los marcadores
    private val _markers = MutableStateFlow<List<Marker>>(emptyList())

    /* Misma lista pero:
        - pública, para poder ser observada por los composables
        - inmutable para que no sea modificada desde fuera del viewmodel */
    val markers: StateFlow<List<Marker>> = _markers

    // Almacenar los tipos de marcador (guardando el ID con el nombre que corresponde)
    private val _markerTypes = MutableStateFlow<Map<Int, String>>(emptyMap())
    val markerTypes: StateFlow<Map<Int, String>> = _markerTypes

    private val _favorites = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val favorites: StateFlow<Map<Int, Boolean>> = _favorites

    // Cargar marcadores y sus tipos
    init {
        loadMarkers()
        loadMarkerTypes()
        loadFavorites()
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

    private fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDao.getAllFavorites().collect { favoriteList ->
                _favorites.value = favoriteList.associate { it.markerId to true }
            }
        }
    }

    fun toggleFavorite(markerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = favorites.value[markerId] == true
            if (isFavorite) {
                favoriteDao.removeFavorite(markerId)
            } else {
                favoriteDao.insertFavorite(Favorite(markerId = markerId))
            }
            loadFavorites()
        }
    }

    suspend fun isFavorite(markerId: Int): Boolean {
        return favoriteDao.isFavorite(markerId)
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