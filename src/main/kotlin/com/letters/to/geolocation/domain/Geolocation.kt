package com.letters.to.geolocation.domain

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

const val REGION_LEVEL = 2
const val CITY_LEVEL = 3

private const val R = 6371e3

@Table(name = "geolocation")
@Entity
class Geolocation(
    @ManyToOne
    @JoinColumn(name = "parent_id")
    val parent: Geolocation?,

    @Column(name = "level")
    val level: Int,

    @Column(name = "name")
    val name: String,

    @Column(name = "fullname")
    val fullname: String,

    @Column(name = "latitude")
    val latitude: Double,

    @Column(name = "longitude")
    val longitude: Double,

    @OneToMany(mappedBy = "parent")
    val children: List<Geolocation> = listOf(),

    @Column(name = "created_date")
    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) {
    init {
        require(parent?.level == level.dec()) { "레벨이 유효하지 않습니다." }
    }

    val isCity: Boolean
        get() = level == CITY_LEVEL

    fun haversine(destination: Geolocation): Double {
        val originLatitude = Math.toRadians(latitude)
        val destinationLatitude = Math.toRadians(destination.latitude)
        val diffLatitude = Math.toRadians(destination.latitude - latitude)
        val diffLongitude = Math.toRadians(destination.longitude - longitude)

        val a = sin(diffLatitude / 2).pow(2) +
            cos(originLatitude) * cos(destinationLatitude) *
            sin(diffLongitude / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c
    }
}
