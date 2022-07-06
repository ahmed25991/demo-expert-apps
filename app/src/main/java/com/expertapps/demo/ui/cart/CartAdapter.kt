package com.expertapps.demo.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.expertapps.demo.base.BaseRecAdapter
import com.expertapps.demo.base.baseAdapter.ItemClickListener
import com.expertapps.demo.databinding.ItemCartBinding
import com.expertapps.demo.models.ProductModel


class CartAdapter (itemClickListener: ItemClickListener) : BaseRecAdapter<ProductModel>(itemClickListener) {


    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return  ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    }

    override fun bind(binding: ViewDataBinding, position: Int, itemClickListener: ItemClickListener) {
        val product = differ.currentList[position]
        binding as ItemCartBinding


  
        binding.tvProductName.text = product.title


    }



    private val differCallback = object : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.prodId == newItem.prodId
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun getItemCount(): Int { return differ.currentList.size }


}