package com.letters.to.topic.application

import com.letters.to.topic.domain.Topic

data class TopicResponse(
    val id: Long,
    val name: String
) {
    constructor(topic: Topic) : this(id = topic.id, name = topic.name)
}
