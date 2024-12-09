package org.iesharia.maproomapp.model


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.iesharia.maproomapp.model.dao.*
import org.iesharia.maproomapp.model.entities.*

@Database(entities = [Marker::class, MarkerType::class, Favorite::class], version = 6)
abstract class MapDatabase : RoomDatabase() {

    abstract fun markerDao(): MarkerDao
    abstract fun markerTypeDao(): MarkerTypeDao
    abstract fun favoriteDao(): FavoriteDAO


    companion object {
        @Volatile
        private var INSTANCE: MapDatabase? = null

        fun getDatabase(context: Context): MapDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MapDatabase::class.java,
                    "map_database"
                )
                    // Si cambia la versión de la base de datos, esta se elimina y se recrea con las modificaciones.
                    .fallbackToDestructiveMigration()
                    // Esto hace que se pierdan los datos, pero es provisional mientras construyo la app
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}