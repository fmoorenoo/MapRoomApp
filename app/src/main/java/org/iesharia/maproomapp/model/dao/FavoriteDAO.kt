package org.iesharia.maproomapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.iesharia.maproomapp.model.entities.Favorite

@Dao
interface FavoriteDAO {
    // Añadir favoritos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    // Quitar favoritos
    @Query("DELETE FROM favorites WHERE markerId = :markerId")
    suspend fun removeFavorite(markerId: Int)

    // Obtener todos los favoritos
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<Favorite>>

    // Ver si un marcador es favorito por su ID
    @Query("SELECT COUNT(*) > 0 FROM favorites WHERE markerId = :markerId")
    suspend fun isFavorite(markerId: Int): Boolean
}
