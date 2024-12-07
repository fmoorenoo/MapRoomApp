package org.iesharia.maproomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.iesharia.maproomapp.model.MapDatabase
import org.iesharia.maproomapp.view.MainApp


class MainActivity : ComponentActivity() {

        private lateinit var database: MapDatabase

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Inicializar la base de datos
            database = MapDatabase.getDatabase(this)

            setContent {
                MainApp(database)
            }
    }
}
