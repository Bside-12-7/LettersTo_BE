package com.letters.to.file.controller

import com.letters.to.file.application.FileDownloadService
import com.letters.to.file.application.FileUploadResponse
import com.letters.to.file.application.FileUploadService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api/files")
class FileController(
    private val fileUploadService: FileUploadService,
    private val fileDownloadService: FileDownloadService
) {

    @PostMapping
    fun uploadUrl(@RequestParam filename: String): FileUploadResponse {
        return fileUploadService.uploadUrl(filename)
    }

    @GetMapping("/{id}")
    fun downloadUrl(@PathVariable id: String): RedirectView {
        return RedirectView(fileDownloadService.downloadUrl(id))
    }
}
