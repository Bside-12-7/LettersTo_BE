package com.letters.to.geolocation.ui

import com.letters.to.geolocation.application.GeolocationQueryService
import com.letters.to.geolocation.application.GeolocationResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/geolocations")
class GeolocationController(private val geolocationQueryService: GeolocationQueryService) {

    @GetMapping("/regions")
    fun findRegions(): List<GeolocationResponse> {
        return geolocationQueryService.findRegions()
    }

    @GetMapping("/regions/{id}/cities")
    fun findCities(@PathVariable id: Long): List<GeolocationResponse> {
        return geolocationQueryService.findCities(id)
    }
}
