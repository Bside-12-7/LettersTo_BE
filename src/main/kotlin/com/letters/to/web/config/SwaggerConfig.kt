package com.letters.to.web.config

import com.letters.to.web.AccessToken
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    init {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(AccessToken::class.java)
    }

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI().addSecurityItem(SecurityRequirement().addList("bearer-key")).components(
            Components().addSecuritySchemes(
                "bearer-key",
                SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
            )
        )
    }
}
