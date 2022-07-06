package com.expertapps.demo.base

import com.expertapps.demo.models.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

abstract class BaseRepository {


    private val defaultDispatcher = Dispatchers.IO




    protected suspend fun <T : Any> buildRoom(task: suspend () -> T) = flow<BaseResponse<T>> {
        emit(BaseResponse.Success(data = task()))
    }.flowOn(defaultDispatcher).onStart { emit(BaseResponse.Loading(loading = true)) }





}