package com.letters.to.topic.ui

import com.letters.to.topic.application.TopicQueryService
import com.letters.to.topic.application.TopicResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/topics")
class TopicController(private val topicQueryService: TopicQueryService) {

    @GetMapping
    fun findAll(): List<TopicResponse> {
        return topicQueryService.findAll()
    }
}
