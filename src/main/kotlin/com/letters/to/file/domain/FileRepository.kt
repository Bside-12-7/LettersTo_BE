package com.letters.to.file.domain

import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository : JpaRepository<File, String>
