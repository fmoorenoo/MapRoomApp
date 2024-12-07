package org.iesharia.maproomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
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

        // Lista mutable para almacenar los marcadores
        val markers = mutableStateListOf<Marker>()

        // Obtener los marcadores de la base de datos
        CoroutineScope(Dispatchers.IO).launch {
            val markerDao = database.markerDao()
            val dbMarkers = markerDao.getAllMarkers()
            markers.addAll(dbMarkers)
        }

        setContent {
            MainApp(database, markers)
        }
    }
}