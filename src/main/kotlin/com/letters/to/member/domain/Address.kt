package com.letters.to.member.domain

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Address(
    @Column(name = "state")
    val state: String,

    @Column(name = "city")
    val city: String,

    @Column(name = "latitude")
    val latitude: Double,

    @Column(name = "longitude")
    val longitude: Double
)
