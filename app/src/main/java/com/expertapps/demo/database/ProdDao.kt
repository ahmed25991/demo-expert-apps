package com.expertapps.demo.database

import androidx.room.*
import com.expertapps.demo.models.ProductModel


@Dao
interface ProdDao {

    @Query("select * from ProductModel ")
    suspend fun queryAllProds(): List<ProductModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProd(offer: ProductModel)


    @Query("DELETE FROM ProductModel")
    suspend fun deleteAllProds()



    @Query("SELECT * FROM ProductModel WHERE  prodId = :prodId ")
    suspend fun retrieveProdIfExist(prodId: Int): List<ProductModel>

    @Query("SELECT COUNT(prodId) FROM ProductModel")
    suspend fun queryCartItemsCount(): Int
}