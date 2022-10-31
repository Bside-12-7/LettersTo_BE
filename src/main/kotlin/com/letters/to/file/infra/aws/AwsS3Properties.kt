package com.letters.to.file.infra.aws

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "aws.s3")
data class AwsS3Properties(
    val bucket: String,
    val accessKeyId: String,
    val secretAccessKey: String,
    val presignedUrlExpiresIn: Long,
    val endpoint: String?,
    val region: String?
)
