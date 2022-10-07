package com.letters.to.topic.application

import com.letters.to.topic.domain.TopicRepository
import org.springframework.stereotype.Service

@Service
class TopicQueryService(private val topicRepository: TopicRepository) {
    fun findAll(): List<TopicResponse> {
        return topicRepository.findAll()
            .map { TopicResponse(it) }
    }
}
