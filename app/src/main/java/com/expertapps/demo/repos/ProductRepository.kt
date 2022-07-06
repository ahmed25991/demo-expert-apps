package com.expertapps.demo.repos

import android.util.Log
import com.expertapps.demo.App
import com.expertapps.demo.base.BaseRepository
import com.expertapps.demo.di.AppDatabase
import com.expertapps.demo.di.PersistenceModule.provideAppDatabase
import com.expertapps.demo.di.UserPreferenceHelper
import com.expertapps.demo.models.BaseResponse
import com.expertapps.demo.models.ProductModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


@ViewModelScoped
class ProductRepository @Inject constructor(
    val db: AppDatabase = provideAppDatabase(App.instance.applicationContext),
    private val pref: UserPreferenceHelper

): BaseRepository() {




    val insertItemIsAddedBeforeResponse = MutableStateFlow<BaseResponse<String>>(BaseResponse.None)

    suspend fun insertProd(prod: ProductModel) {
            val queryList = db.prodDao.retrieveProdIfExist(prod.prodId)
        if (queryList.isNullOrEmpty()) {
            db.prodDao.insertProd(prod)
            itemsCountInCartResponse.emitAll(flow = buildRoom {
                db.prodDao.queryCartItemsCount()})
                pref.save(Calendar.getInstance().time,"cart_time")

            } else { insertItemIsAddedBeforeResponse.emitAll(flow = buildRoom { "Product is added before"}) }


    }


    val prodsResponse = MutableStateFlow<BaseResponse<List<ProductModel>>>(BaseResponse.None)

    suspend fun getCartProds() = prodsResponse.emitAll(flow = buildRoom { db.prodDao.queryAllProds()})



    val itemsCountInCartResponse = MutableStateFlow<BaseResponse<Int>>(BaseResponse.None)
    suspend fun queryCartItemsCount() = itemsCountInCartResponse.emitAll(flow = buildRoom {
        db.prodDao.queryCartItemsCount()})



    suspend fun deleteAllProds() = withContext(Dispatchers.IO) {


        db.prodDao.deleteAllProds()
        pref.clear("cart_time")
        itemsCountInCartResponse.emitAll(flow = buildRoom { db.prodDao.queryCartItemsCount()})
        prodsResponse.emitAll(flow = buildRoom { db.prodDao.queryAllProds()})

    }


}