package org.iesharia.maproomapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.iesharia.maproomapp.model.entities.Favorite

@Dao
interface FavoriteDAO {
    // Añadir favoritoa
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE markerId = :markerId")
    suspend fun removeFavorite(markerId: Int)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE markerId = :markerId)")
    suspend fun isFavorite(markerId: Int): Boolean
}
