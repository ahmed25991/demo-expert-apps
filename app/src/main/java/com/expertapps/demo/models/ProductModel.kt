package com.expertapps.demo.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class ProductModel(
    @PrimaryKey
    @field:SerializedName("prodId")
    var prodId: Int ,
    @field:SerializedName("title")
    var title: String? = ""
    ) : Parcelable