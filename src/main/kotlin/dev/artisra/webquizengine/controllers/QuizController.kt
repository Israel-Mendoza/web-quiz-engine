package dev.artisra.webquizengine.controllers

import dev.artisra.webquizengine.exceptions.QuizNotFound
import dev.artisra.webquizengine.models.*
import dev.artisra.webquizengine.services.QuizService
import dev.artisra.webquizengine.services.ResolvedQuizService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class QuizController(
    private val quizService: QuizService,
    private val resolvedQuizService: ResolvedQuizService,
) {

    @PostMapping("/quizzes/multiple")
    fun createMultipleQuizzes(
        @RequestBody quizRequests: List<QuizRequest>,
        @AuthenticationPrincipal user: UserDetails,
    ): ResponseEntity<List<QuizResponse>> {
        val createdQuizzes = quizRequests.map {
            quizService.createNewQuiz(it, user)
        }
        return ResponseEntity(createdQuizzes, HttpStatus.CREATED)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/quizzes")
    fun createNewQuiz(
        @Valid @RequestBody quizRequest: QuizRequest,
        @AuthenticationPrincipal user: UserDetails,
    ): ResponseEntity<QuizResponse> {
        val quizResponse = quizService.createNewQuiz(quizRequest, user)
        return ResponseEntity.ok(quizResponse)
    }

    @PostMapping("/quizzes/{id}/solve")
    fun solveSelectedQuiz(
        @PathVariable id: Int,
        @RequestBody solutionRequest: SolutionRequest,
        @AuthenticationPrincipal user: UserDetails,
    ): ResponseEntity<SolutionFeedback> {
        val answerIsCorrect = resolvedQuizService.solveQuiz(id, solutionRequest.answer)
        if (answerIsCorrect) {
            resolvedQuizService.saveResolvedQuiz(id, user)
            return ResponseEntity.ok(SolutionFeedback(true, "Congratulations, you're right!"))
        }
        return ResponseEntity.ok(SolutionFeedback(false, "Wrong answer! Please, try again."))
    }

    @GetMapping("/quizzes")
    fun getAllQuizzes(@PageableDefault(size = 10) pageable: Pageable): ResponseEntity<Page<QuizResponse>> {
        val quizzes = quizService.getQuizPage(pageable)
        return ResponseEntity.ok(quizzes)
    }

    @GetMapping("/quizzes/{id}")
    fun getStoredQuiz(@PathVariable id: Int): ResponseEntity<QuizResponse> {
        val requestedQuiz = quizService.getQuizById(id) ?: throw QuizNotFound()
        return ResponseEntity.ok(requestedQuiz)
    }

    @GetMapping("/quizzes/completed")
    fun getCompletedQuizzes(
        @PageableDefault(size = 10, sort = ["completedAt"], direction = Sort.Direction.DESC) pageable: Pageable,
        @AuthenticationPrincipal user: UserDetails,
    ): ResponseEntity<Page<ResolvedQuizResponse>> {
        val quizzes = resolvedQuizService.getResolvedQuizPage(pageable, user)
        return ResponseEntity.ok(quizzes)
    }

    @DeleteMapping("/quizzes/{id}")
    fun deleteQuiz(@PathVariable id: Int, @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<Any> {
        val wasDeleted = quizService.deleteQuizById(id, userDetails)
        return if (wasDeleted) ResponseEntity(HttpStatus.NO_CONTENT) else ResponseEntity(HttpStatus.FORBIDDEN)
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(QuizController::class.java)
    }
}