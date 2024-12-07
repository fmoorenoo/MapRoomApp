package org.iesharia.maproomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.iesharia.maproomapp.model.MapDatabase
import org.iesharia.maproomapp.view.MainApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.iesharia.maproomapp.model.Marker
import org.iesharia.maproomapp.model.MarkerType

class MainActivity : ComponentActivity() {

    private lateinit var database: MapDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos
        database = MapDatabase.getDatabase(this)

        // Insertar los tipos de marcadores en la base de datos
        // insertMarkerTypes()

        // Insertar marcadores en la base de datos
        // insertMarkers()

        setContent {
            MainApp(database)
        }
    }


    // Función para insertar tipos de marcadores
    /*
    private fun insertMarkerTypes() {
        val markerTypeDao = database.markerTypeDao()
        val markerTypes = listOf(
            MarkerType(name = "Ocio"),
            MarkerType(name = "Comida"),
            MarkerType(name = "Monumento"),
            MarkerType(name = "Otro")
        )

        // Insertar los tipos de marcadores en un hilo diferente al de la interfaz
        CoroutineScope(Dispatchers.IO).launch {
            markerTypes.forEach { markerType ->
                markerTypeDao.insertMarkerType(markerType)
            }
        }
    }


    // Función para insertar marcadores
    private fun insertMarkers() {
        val markerDao = database.markerDao()

        // Lista de marcadores a añadir
        val markers = listOf(
            Marker(
                name = "Torre Eiffel",
                location = "48.86169201033145, 2.293716143694126",
                markerTypeId = 3 // Tipo: Monumento
            ),
            Marker(
                name = "Arco del Triunfo",
                location = "48.877500049408596, 2.295089433250181",
                markerTypeId = 3 // Tipo: Monumento
            ),
            Marker(
                name = "Tasca",
                location = "48.85452329722391, 2.296511294801642",
                markerTypeId = 2 // Tipo: Comida
            )
        )

        // Insertar los marcadores en un hilo diferente al de la interfaz
        CoroutineScope(Dispatchers.IO).launch {
            markers.forEach { marker ->
                markerDao.insert(marker)
            }
        }
    }
    */
}