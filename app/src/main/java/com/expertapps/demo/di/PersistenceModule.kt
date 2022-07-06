package com.expertapps.demo.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.expertapps.demo.database.ProdDao
import com.expertapps.demo.models.ProductModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private const val DATABASE_NAME = "DatabaseGateway.db"



@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {


    @Singleton
    @Provides
    fun providePref(@ApplicationContext context: Context): UserPreferenceHelper = UserPreferenceHelper(application = context)



    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }


}


@Database(
    entities = [ProductModel::class],
    version = 11,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val prodDao: ProdDao
}

