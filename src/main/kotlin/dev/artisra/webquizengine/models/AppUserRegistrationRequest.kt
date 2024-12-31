package dev.artisra.webquizengine.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class AppUserRegistrationRequest(
    @field:NotBlank(message = "must not be blank")
    @field:Pattern(regexp = ".+@.+\\..+", message = "invalid email format")
    val email: String,
    @field:Size(min = 5, message = "must contain at least 5 characters")
    val password: String
)