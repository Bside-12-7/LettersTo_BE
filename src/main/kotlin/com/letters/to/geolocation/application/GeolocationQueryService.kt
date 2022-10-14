package com.letters.to.geolocation.application

import com.letters.to.geolocation.domain.GeolocationRepository
import com.letters.to.geolocation.domain.REGION_LEVEL
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class GeolocationQueryService(private val geolocationRepository: GeolocationRepository) {
    fun findRegions(): List<GeolocationResponse> {
        return geolocationRepository.findAllByLevel(REGION_LEVEL)
            .map {
                GeolocationResponse(
                    id = it.id,
                    name = it.name
                )
            }
    }

    fun findCities(id: Long): List<GeolocationResponse> {
        val geolocation = geolocationRepository.findByIdOrNull(id) ?: return emptyList()

        if (geolocation.level == REGION_LEVEL) {
            return geolocation.children
                .map {
                    GeolocationResponse(
                        id = it.id,
                        name = it.name
                    )
                }
        }

        return emptyList()
    }
}
