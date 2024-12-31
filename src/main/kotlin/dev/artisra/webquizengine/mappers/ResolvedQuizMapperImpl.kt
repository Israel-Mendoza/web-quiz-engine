package dev.artisra.webquizengine.mappers

import dev.artisra.webquizengine.entities.ResolvedQuiz
import dev.artisra.webquizengine.models.ResolvedQuizResponse
import org.springframework.stereotype.Component

@Component
class ResolvedQuizMapperImpl : ResolvedQuizMapper {
    override fun entityToResponse(resolvedQuiz: ResolvedQuiz): ResolvedQuizResponse {
        return ResolvedQuizResponse(
            id =  resolvedQuiz.quiz?.id ?: 0,
            completedAt = resolvedQuiz.completedAt
        )
    }
}