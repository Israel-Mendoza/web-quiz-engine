package dev.artisra.webquizengine.mappers

import dev.artisra.webquizengine.entities.Quiz
import dev.artisra.webquizengine.models.QuizResponse
import dev.artisra.webquizengine.models.QuizRequest

interface QuizMapper {
    fun requestToEntity(quizRequest: QuizRequest): Quiz
    fun entityToResponse(quizEntity: Quiz): QuizResponse
}