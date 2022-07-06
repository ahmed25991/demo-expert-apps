package com.expertapps.demo.base


import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.expertapps.demo.R
import com.expertapps.demo.base.baseAdapter.BaseViewHolder
import com.expertapps.demo.base.baseAdapter.ItemClickListener

abstract class BaseRecAdapter<T>(var itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<BaseViewHolder<ViewDataBinding>>() {

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        (holder as BaseViewHolder<*>).binding.root.setTag(R.string.position, position)
        bind(holder.binding, position,itemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getViewHolder(parent, viewType)

    open fun getViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(createBinding(parent, viewType))

    abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    protected abstract fun bind(binding: ViewDataBinding, position: Int,itemClickListener: ItemClickListener)
}