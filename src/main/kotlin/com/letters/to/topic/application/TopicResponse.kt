package com.letters.to.topic.application

import com.letters.to.topic.domain.Topic
import com.letters.to.topic.domain.TopicGroup

data class TopicResponse(
    val id: Long,
    val group: TopicGroup,
    val name: String
) {
    constructor(topic: Topic) : this(id = topic.id, group = topic.group, name = topic.name)
}
