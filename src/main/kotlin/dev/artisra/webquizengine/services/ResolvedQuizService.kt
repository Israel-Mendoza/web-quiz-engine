package dev.artisra.webquizengine.services

import dev.artisra.webquizengine.entities.ResolvedQuiz
import dev.artisra.webquizengine.exceptions.QuizNotFound
import dev.artisra.webquizengine.mappers.QuizMapper
import dev.artisra.webquizengine.mappers.ResolvedQuizMapper
import dev.artisra.webquizengine.models.ResolvedQuizResponse
import dev.artisra.webquizengine.respositories.QuizRepository
import dev.artisra.webquizengine.respositories.ResolvedQuizRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ResolvedQuizService(
    private val quizRepository: QuizRepository,
    private val resolvedQuizRepository: ResolvedQuizRepository,
    private val appUserService: AppUserService,
    private val quizMapper: QuizMapper,
    private val resolvedQuizMapper: ResolvedQuizMapper
) {
    fun solveQuiz(quizId: Int, userAnswers: List<Int>): Boolean {
        val selectedQuiz = quizRepository.findById(quizId).getOrNull() ?: throw QuizNotFound()
        return userAnswers.toSet() == quizMapper.entityToResponse(selectedQuiz).answer.toSet()
    }

    fun saveResolvedQuiz(quizId: Int, userDetails: UserDetails) {
        val resolvedQuiz = ResolvedQuiz(
            quiz = quizRepository.findById(quizId).getOrNull() ?: throw QuizNotFound(),
            user = appUserService.getUserByUserDetails(userDetails)
        )
        resolvedQuizRepository.save(resolvedQuiz)
    }

    fun getResolvedQuizPage(pageable: Pageable, user: UserDetails): Page<ResolvedQuizResponse> {
        val appUser = appUserService.getUserByUserDetails(user) ?: throw UsernameNotFoundException("User not found!")
        val resolvedQuizPage = resolvedQuizRepository.findAllByUserId(appUser.id, pageable)
        return resolvedQuizPage.map {
            resolvedQuizMapper.entityToResponse(it)
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ResolvedQuizService::class.java)
    }
}