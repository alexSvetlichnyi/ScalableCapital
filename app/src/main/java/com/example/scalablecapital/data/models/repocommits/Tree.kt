package com.example.scalablecapital.data.models.repocommits

import com.google.gson.annotations.SerializedName


data class Tree (

  @SerializedName("sha" ) var sha : String? = null,
  @SerializedName("url" ) var url : String? = null

)