package com.letters.to.auth.domain

import com.letters.to.file.domain.FileRepository
import com.letters.to.letter.application.LetterWriteEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class LetterWriteListener(private val fileRepository: FileRepository) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun listen(event: LetterWriteEvent) {
        val files = fileRepository.findAllById(event.files)

        files.forEach { it.confirm() }
    }
}
