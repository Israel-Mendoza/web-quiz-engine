package dev.artisra.webquizengine.mappers

import dev.artisra.webquizengine.entities.Answer
import dev.artisra.webquizengine.entities.Option
import dev.artisra.webquizengine.entities.Quiz
import dev.artisra.webquizengine.models.QuizResponse
import dev.artisra.webquizengine.models.QuizRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class QuizMapperImpl : QuizMapper {
    override fun requestToEntity(quizRequest: QuizRequest): Quiz {
        val newQuizEntity = Quiz(
            title = quizRequest.title,
            text = quizRequest.text
        )
        val options = quizRequest.options.map { Option(option = it) }
        val answers = quizRequest.answer.map { Answer(answer = it) }

        options.forEach { it.quiz = newQuizEntity }
        answers.forEach { it.quiz = newQuizEntity }

        newQuizEntity.options = options
        newQuizEntity.answer = answers

        return newQuizEntity
    }

    override fun entityToResponse(quizEntity: Quiz): QuizResponse {
        val options = quizEntity.options.map { it.option }
        val answers = quizEntity.answer.map { it.answer }
        val newQuiz = QuizResponse(
            id = quizEntity.id,
            title = quizEntity.title,
            text = quizEntity.text,
            options = options,
            answer = answers
        )
        return newQuiz
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(QuizMapper::class.java)
    }
}