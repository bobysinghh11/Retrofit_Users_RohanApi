package com.stuffshuf.retrofit_users_rohanapi


data class Address(
    val street:String,
    val suite:String,
    val city:String

)

data class Users(

    val id:Int,
    val name:String,
    val username:String,
    val email:String,
    val address: Address
)

