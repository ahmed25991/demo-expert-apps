package com.expertapps.demo.base


import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.expertapps.demo.models.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


abstract class BaseActivity<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : AppCompatActivity() {

    private lateinit var _binding: T
    protected val binding: T get() = _binding

    protected inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView<T>(this@BaseActivity, contentLayoutId)
        _binding.executePendingBindings()
        _binding.lifecycleOwner = this
    }


     fun <T: Any> Flow<BaseResponse<T>>.onSuccessCollect(onSuccess: (T) -> Unit){
        lifecycleScope.launchWhenStarted {
            this@onSuccessCollect.collect { response->
                when (response){
                    is BaseResponse.Loading -> Unit
                    is BaseResponse.Success<*> -> onSuccess(response.data as T)
                    is BaseResponse.None -> Unit
                }
            }
        }
    }






}



