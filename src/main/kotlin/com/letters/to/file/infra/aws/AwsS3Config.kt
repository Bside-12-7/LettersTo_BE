package com.letters.to.file.infra.aws

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import java.net.URI

@Configuration
class AwsS3Config(
    private val awsS3Properties: AwsS3Properties
) {
    @Bean
    fun s3Presigner(): S3Presigner {
        val credentials = AwsBasicCredentials.create(awsS3Properties.accessKeyId, awsS3Properties.secretAccessKey)
        val credentialsProvider = StaticCredentialsProvider.create(credentials)

        val builder = S3Presigner.builder()
            .region(awsS3Properties.region?.let { Region.of(it) } ?: Region.AP_NORTHEAST_2)
            .credentialsProvider(credentialsProvider)

        awsS3Properties.endpoint?.let { builder.endpointOverride(URI.create(it)) }

        return builder.build()
    }

    @Bean
    fun s3Client(): S3Client {
        val credentials = AwsBasicCredentials.create(awsS3Properties.accessKeyId, awsS3Properties.secretAccessKey)
        val credentialsProvider = StaticCredentialsProvider.create(credentials)

        val builder = S3Client.builder()
            .region(awsS3Properties.region?.let { Region.of(it) } ?: Region.AP_NORTHEAST_2)
            .credentialsProvider(credentialsProvider)

        awsS3Properties.endpoint?.let { builder.endpointOverride(URI.create(it)) }

        return builder.build()
    }
}
