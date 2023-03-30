package com.prateek.mycbseguide.models

import com.google.gson.annotations.SerializedName

data class ApiResponse (
    @SerializedName("status"     ) var status     : String?               = null,
    @SerializedName("categories" ) var categories : ArrayList<Categories> = arrayListOf()
)
