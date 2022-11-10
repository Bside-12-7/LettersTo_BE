package com.letters.to.letter.domain

import com.letters.to.member.domain.Member
import com.letters.to.stamp.domain.Stamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Version

@Table(name = "letter")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class Letter(
    @Column(name = "title")
    open val title: Title = Title.UNTITLED,

    @Column(name = "content", length = 1000)
    open val content: Content,

    @OneToMany(mappedBy = "letter")
    open val pictures: MutableList<Picture> = mutableListOf(),

    @Column(name = "paper_type")
    @Enumerated(EnumType.STRING)
    open val paperType: PaperType,

    @Column(name = "paper_color")
    @Enumerated(EnumType.STRING)
    open val paperColor: PaperColor,

    @ManyToOne
    @JoinColumn(name = "stamp_id")
    open val stamp: Stamp,

    @ManyToOne
    @JoinColumn(name = "from_member_id")
    open val fromMember: Member,

    @Column(name = "created_date")
    open val createdDate: LocalDateTime = LocalDateTime.now(),

    @Version
    @Column(name = "version")
    open val version: Int = 0,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0L
) {
    fun attachPictures(pictures: List<Picture>) {
        this.pictures.addAll(pictures)
        this.pictures.forEach {
            it.letter = this
        }
    }
}
