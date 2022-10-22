package com.letters.to.file.infra.aws

import com.letters.to.file.application.FileUploadResponse
import com.letters.to.file.application.FileUploadService
import com.letters.to.file.domain.File
import com.letters.to.file.domain.FileRepository
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Service
class AwsS3ImageUploadService(
    private val s3Presigner: S3Presigner,
    private val s3Properties: AwsS3Properties,
    private val fileRepository: FileRepository
) : FileUploadService {
    override fun uploadUrl(filename: String): FileUploadResponse {
        val file = File(
            bucket = s3Properties.bucket,
            key = generateKey(filename),
            expiredDate = LocalDateTime.now().plusSeconds(s3Properties.presignedUrlExpiresIn)
        )

        val objectRequest = PutObjectRequest.builder()
            .bucket(file.bucket)
            .key(file.key)
            .contentType("image/*")
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofSeconds(s3Properties.presignedUrlExpiresIn))
            .putObjectRequest(objectRequest)
            .build()

        val uploadUrl = s3Presigner.presignPutObject(presignRequest).url().toExternalForm()

        fileRepository.save(file)

        return FileUploadResponse(
            id = file.id,
            uploadUrl = uploadUrl,
        )
    }

    private fun generateKey(filename: String): String {
        val uuid = UUID.randomUUID().toString()

        return "$uuid.${filename.split(".").last()}"
    }
}
