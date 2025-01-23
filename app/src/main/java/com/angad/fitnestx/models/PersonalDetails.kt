package com.angad.fitnestx.models

data class PersonalDetails(
    val gender: String? = null,
    var dob: String? = null,
    var weight: Float? = null,
    var height: Float? = null,
    var uid: String? = null
)
