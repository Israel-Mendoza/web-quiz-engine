package dev.artisra.webquizengine.services

import dev.artisra.webquizengine.exceptions.QuizNotFound
import dev.artisra.webquizengine.mappers.QuizMapper
import dev.artisra.webquizengine.models.QuizResponse
import dev.artisra.webquizengine.models.QuizRequest
import dev.artisra.webquizengine.respositories.QuizRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class QuizService(
    private val quizRepository: QuizRepository,
    private val quizMapper: QuizMapper,
    private val appUserService: AppUserService,
) {

    fun createNewQuiz(quizRequest: QuizRequest, userDetails: UserDetails): QuizResponse {
        var quiz = quizMapper.requestToEntity(quizRequest)
        quiz.user = appUserService.getUserByUserDetails(userDetails)
        quiz = quizRepository.save(quiz)
        return quizMapper.entityToResponse(quiz)
    }

    fun getAllQuizzes() = quizRepository.findAll()
        .map { quizMapper.entityToResponse(it) }


    fun getQuizById(id: Int): QuizResponse? {
        val quiz = quizRepository.findById(id).getOrNull()
        return if (quiz == null) null else quizMapper.entityToResponse(quiz)
    }

    fun getQuizPage(pageable: Pageable): Page<QuizResponse> {
        val quizzes = quizRepository.findAll(pageable)
        return quizzes.map { quizEntity ->
            quizMapper.entityToResponse(quizEntity)
        }
    }

    fun deleteQuizById(id: Int, user: UserDetails): Boolean {
        val appUser = appUserService.getUserByUserDetails(user) ?: throw UsernameNotFoundException("User not found!")
        val quiz = quizRepository.findById(id).getOrNull() ?: throw QuizNotFound()
        if (appUser.id == quiz.user?.id) {
            quizRepository.delete(quiz)
            return true
        }
        return false
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(QuizService::class.java)
    }
}