package com.prateek.mycbseguide.models

import com.google.gson.annotations.SerializedName

data class Categories (

    @SerializedName("id"          ) var id         : Int?                = null,
    @SerializedName("name"        ) var name       : String?             = null,
    @SerializedName("weight"      ) var weight     : Int?                = null,
    @SerializedName("mobile_logo" ) var mobileLogo : String?             = null,

)