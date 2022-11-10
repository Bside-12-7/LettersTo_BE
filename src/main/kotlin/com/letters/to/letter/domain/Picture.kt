package com.letters.to.letter.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(name = "picture")
@Entity
class Picture(
    @Column(name = "file_id")
    val fileId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_id")
    var letter: Letter? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
