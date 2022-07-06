package com.expertapps.demo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.expertapps.demo.base.BaseRecAdapter
import com.expertapps.demo.base.baseAdapter.ItemClickListener
import com.expertapps.demo.databinding.ItemHomeProductBinding
import com.expertapps.demo.models.ProductModel


class ProductsAdapter (itemClickListener: ItemClickListener) : BaseRecAdapter<ProductModel>(itemClickListener) {


    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return  ItemHomeProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    }

    override fun bind(binding: ViewDataBinding, position: Int, itemClickListener: ItemClickListener) {
        val product = differ.currentList[position]
        binding as ItemHomeProductBinding


  
        binding.tvProductName.text = product.title



        binding.btnAddToCart.setOnClickListener {
            itemClickListener.onItemClick(product)
        }

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