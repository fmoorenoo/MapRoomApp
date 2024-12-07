package org.iesharia.maproomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.iesharia.maproomapp.model.MapDatabase
import org.iesharia.maproomapp.view.MainApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.iesharia.maproomapp.model.MarkerType

class MainActivity : ComponentActivity() {

    private lateinit var database: MapDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos
        database = MapDatabase.getDatabase(this)

        // Insertar los tipos de marcadores en la base de datos
        // insertMarkerTypes()

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
    */
}