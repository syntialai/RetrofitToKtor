package com.syntia.moviecatalogue.core.config.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.syntia.moviecatalogue.core.data.source.local.dao.FavoriteMoviesDAO
import com.syntia.moviecatalogue.core.data.source.local.dao.FavoriteTvShowsDAO
import com.syntia.moviecatalogue.core.data.source.local.entity.movie.MovieEntity
import com.syntia.moviecatalogue.core.data.source.local.entity.tvShows.TvShowsEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@InternalCoroutinesApi
@Database(entities = [MovieEntity::class, TvShowsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

  abstract fun favoriteMoviesDAO(): FavoriteMoviesDAO

  abstract fun favoriteTvShowsDAO(): FavoriteTvShowsDAO

  companion object {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    @JvmStatic
    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: run {
        synchronized(AppDatabase::class.java) {
          INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
              DatabaseConstants.DB_NAME)
              .openHelperFactory(getSupportFactory())
              .build()
        }
        INSTANCE as AppDatabase
      }
    }

    private fun getSupportFactory(): SupportFactory {
      return SupportFactory(SQLiteDatabase.getBytes(DatabaseConstants.NAME.toCharArray()))
    }
  }
}