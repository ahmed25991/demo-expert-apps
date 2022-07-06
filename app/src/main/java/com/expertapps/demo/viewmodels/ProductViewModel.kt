package com.expertapps.demo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.expertapps.demo.models.BaseResponse
import com.expertapps.demo.models.ProductModel
import com.expertapps.demo.repos.ProductRepository
import com.window.wndo.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class ProductViewModel@Inject constructor(private val repo: ProductRepository) : BaseViewModel() {

    val prodsResponse= repo.prodsResponse.asStateFlow()
    fun fetchProducts() = launchTask { repo.getCartProds() }



    val insertItemIsAddedBeforeResponse= repo.insertItemIsAddedBeforeResponse.asStateFlow()
    val itemsCountInCartResponse= repo.itemsCountInCartResponse.asStateFlow()
    fun insertProd(productModel :ProductModel) = launchTask { repo.insertProd(prod = productModel) }
    fun queryCartItemsCount() = launchTask { repo.queryCartItemsCount() }
    fun deleteAllProds() = launchTask { repo.deleteAllProds() }


}

