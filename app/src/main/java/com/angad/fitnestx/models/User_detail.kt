package com.angad.fitnestx.models

data class User_detail(
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String,
    var password: String,
    var uid: String? = null
)