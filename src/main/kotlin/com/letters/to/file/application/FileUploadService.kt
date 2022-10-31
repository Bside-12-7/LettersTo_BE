package com.letters.to.file.application

interface FileUploadService {
    fun uploadUrl(filename: String): FileUploadResponse
}
