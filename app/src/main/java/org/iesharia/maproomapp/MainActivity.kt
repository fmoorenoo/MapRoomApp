package org.iesharia.maproomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.iesharia.maproomapp.viewmodel.MapViewModel
import org.iesharia.maproomapp.model.MapDatabase
import org.iesharia.maproomapp.view.MainApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar la base de datos
        val database = MapDatabase.getDatabase(this)
        val markerDao = database.markerDao()
        val markerTypeDao = database.markerTypeDao()
        val favoriteDao = database.favoriteDao()

        // Crear el ViewModel
        val mapViewModel = MapViewModel(markerDao, markerTypeDao, favoriteDao)

        setContent {
            MainApp(mapViewModel)
        }
    }
}