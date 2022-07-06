package com.expertapps.demo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.expertapps.demo.R
import com.expertapps.demo.base.BaseActivity
import com.expertapps.demo.base.baseAdapter.ItemClickListener
import com.expertapps.demo.databinding.ActivityProductsBinding
import com.expertapps.demo.di.UserPreferenceHelper
import com.expertapps.demo.models.ProductModel
import com.expertapps.demo.ui.cart.CartActivity
import com.expertapps.demo.utils.AppUtils.getDaysDifference
import com.expertapps.demo.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class ProductsActivity : BaseActivity<ActivityProductsBinding>(R.layout.activity_products), ItemClickListener {


    private val viewModel by viewModels<ProductViewModel>()

    @Inject
    lateinit var pref : UserPreferenceHelper

    private val adapter: ProductsAdapter = ProductsAdapter(this@ProductsActivity)

    val productsList = ArrayList<ProductModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.adapter = adapter




        check3DaysPassed()
        showProducts()
        viewModel.queryCartItemsCount()



        binding.ivCart.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
            finish()
        }



        observeAddToCartListener()

    }


    private fun observeAddToCartListener(){
        viewModel.insertItemIsAddedBeforeResponse.onSuccessCollect { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }


        viewModel.itemsCountInCartResponse.onSuccessCollect { itemsCount ->
            if (itemsCount > 0){
                binding.tvItemsCount.text = itemsCount.toString()
            }else{
                binding.tvItemsCount.text = ""
            }
        }


    }


    override fun <T> onItemClick(item: T, type: Int?) {
        val model = (item as ProductModel)
        viewModel.insertProd(model)

    }


    private fun showProducts(){
        productsList.clear()
        productsList.add(ProductModel(1,"Samsung galaxy a52"))
        productsList.add(ProductModel(2,"Iphone pro max"))
        productsList.add(ProductModel(3,"nokia 3sAd"))
        productsList.add(ProductModel(4,"Huawei GR5"))
        productsList.add(ProductModel(5,"Oppo F7"))
        productsList.add(ProductModel(6,"Realme NoteBad"))


        adapter.differ.submitList(productsList)
    }


    private fun check3DaysPassed(){
        if (pref.retrieve<Date>("cart_time")!= null) {
            val days: Int = getDaysDifference(pref.retrieve<Date>("cart_time"),Calendar.getInstance().time)

            if (days >= 3){
                viewModel.deleteAllProds()
            }
        }
    }


}

