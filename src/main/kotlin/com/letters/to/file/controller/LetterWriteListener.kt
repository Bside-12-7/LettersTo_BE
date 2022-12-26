package com.letters.to.file.controller

import com.letters.to.event.NotifyClient
import com.letters.to.file.domain.FileRepository
import com.letters.to.letter.application.LetterWriteEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class LetterWriteListener(private val fileRepository: FileRepository, private val notifyClient: NotifyClient) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listen(event: LetterWriteEvent) {
        val files = fileRepository.findAllById(event.files)

        files.forEach { it.confirm() }

        if (event.delivered.not()) {
            return
        }

        notifyClient.notify(
            title = "[${event.fromMember}]님에게 답장이 왔어요!",
            content = event.content,
            type = "LETTER",
            intent = "{\"letterId\": ${event.id}}",
            memberId = event.toMemberId
        )
    }
}
