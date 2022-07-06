package com.expertapps.demo.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import com.expertapps.demo.R
import com.expertapps.demo.base.BaseActivity
import com.expertapps.demo.base.baseAdapter.ItemClickListener
import com.expertapps.demo.databinding.ActivityCartBinding
import com.expertapps.demo.di.UserPreferenceHelper
import com.expertapps.demo.ui.ProductsActivity
import com.expertapps.demo.utils.AppUtils.getDaysDifference
import com.expertapps.demo.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>(R.layout.activity_cart), ItemClickListener {


    private val viewModel by viewModels<ProductViewModel>()

    @Inject
    lateinit var pref : UserPreferenceHelper

    private val adapter: CartAdapter = CartAdapter(this@CartActivity)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.adapter = adapter



        check3DaysPassed()
        viewModel.fetchProducts()
        viewModel.queryCartItemsCount()



        observeProductsListener()
    }


    private fun observeProductsListener(){
        viewModel.prodsResponse.onSuccessCollect { cartList ->
            if (cartList.isNullOrEmpty()){
                binding.emptyCart.visibility = VISIBLE
            }else{
                binding.emptyCart.visibility = GONE
                adapter.differ.submitList(cartList)
            }


        }


        viewModel.itemsCountInCartResponse.onSuccessCollect { itemsCount ->
            if (itemsCount > 0){
                binding.tvPageTitle.text =getString(R.string.label_cart,itemsCount.toString())
            }else{
                binding.tvPageTitle.text = getString(R.string.label_cart,"0")
            }
        }



    }


    override fun <T> onItemClick(item: T, type: Int?) {}


    private fun check3DaysPassed(){
        if (pref.retrieve<Date>("cart_time")!= null) {
            val days: Int = getDaysDifference(pref.retrieve<Date>("cart_time"),Calendar.getInstance().time)

            if (days >= 3){
                viewModel.deleteAllProds()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, ProductsActivity::class.java))
        finish()
    }



}

