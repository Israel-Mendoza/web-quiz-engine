package dev.artisra.webquizengine.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class QuizRequest(
    @field:NotNull(message = "must not be null")
    @field:NotBlank(message = "must not be blank")
    val title: String,

    @field:NotNull(message = "must not be null")
    @field:NotBlank(message = "must not be blank")
    val text: String,

    @field:Size(min = 2, message = "must contain at least 2 options")
    val options: List<String>,
    val answer: List<Int> = emptyList()
)