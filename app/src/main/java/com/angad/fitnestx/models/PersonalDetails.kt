package com.angad.fitnestx.models

data class PersonalDetails(
    val gender: String? = null,
    var dob: String? = null,
    var weight: String? = null,
    var height: Int? = null,
    var uid: String? = null
)
