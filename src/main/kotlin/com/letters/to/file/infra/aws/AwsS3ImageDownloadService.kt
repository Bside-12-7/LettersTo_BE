package com.letters.to.file.infra.aws

import com.letters.to.file.application.FileDownloadService
import com.letters.to.file.application.FileNotFoundException
import com.letters.to.file.domain.FileRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client

@Service
class AwsS3ImageDownloadService(
    private val s3Client: S3Client,
    private val fileRepository: FileRepository
) : FileDownloadService {
    override fun downloadUrl(id: String): String {
        val file = fileRepository.findByIdOrNull(id) ?: throw FileNotFoundException()

        return s3Client.utilities()
            .getUrl {
                it.bucket(file.bucket)
                    .key(file.key)
                    .build()
            }.toExternalForm()
    }
}
