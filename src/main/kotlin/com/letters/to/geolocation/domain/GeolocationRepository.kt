package com.letters.to.geolocation.domain

import org.springframework.data.jpa.repository.JpaRepository

interface GeolocationRepository : JpaRepository<Geolocation, Long> {
    fun findAllByLevel(level: Int): List<Geolocation>
}
