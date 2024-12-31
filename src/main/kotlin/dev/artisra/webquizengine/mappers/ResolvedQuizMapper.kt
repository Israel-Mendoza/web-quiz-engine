package dev.artisra.webquizengine.mappers

import dev.artisra.webquizengine.entities.ResolvedQuiz
import dev.artisra.webquizengine.models.ResolvedQuizResponse

interface ResolvedQuizMapper {
    fun entityToResponse(resolvedQuiz: ResolvedQuiz): ResolvedQuizResponse
}