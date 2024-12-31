package dev.artisra.webquizengine.models

import com.fasterxml.jackson.annotation.JsonIgnore

data class QuizResponse(
    val id: Int,
    val title: String,
    val text: String,
    val options: List<String>,
    @JsonIgnore
    val answer: List<Int>
)