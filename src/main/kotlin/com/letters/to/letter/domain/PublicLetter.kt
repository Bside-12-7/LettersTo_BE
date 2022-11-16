package com.letters.to.letter.domain

import com.letters.to.member.domain.Member
import com.letters.to.personality.domain.Personality
import com.letters.to.stamp.domain.Stamp
import com.letters.to.topic.domain.Topic
import javax.persistence.CascadeType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
@DiscriminatorValue("PUBLIC")
class PublicLetter(
    title: Title = Title.UNTITLED,
    content: Content,
    pictures: MutableList<Picture> = mutableListOf(),
    paperType: PaperType,
    paperColor: PaperColor,
    alignType: AlignType,
    stamp: Stamp,
    fromMember: Member,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "letter_topic",
        joinColumns = [JoinColumn(name = "letter_id")],
        inverseJoinColumns = [JoinColumn(name = "topic_id")]
    )
    val topics: MutableList<Topic> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "letter_personality",
        joinColumns = [JoinColumn(name = "letter_id")],
        inverseJoinColumns = [JoinColumn(name = "personality_id")]
    )
    val personalities: MutableList<Personality> = mutableListOf(),
) : Letter(
    title = title,
    content = content,
    pictures = pictures,
    paperType = paperType,
    paperColor = paperColor,
    alignType = alignType,
    stamp = stamp,
    fromMember = fromMember
)
