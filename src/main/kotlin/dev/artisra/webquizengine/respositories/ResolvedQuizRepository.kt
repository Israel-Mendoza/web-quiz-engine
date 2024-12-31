package dev.artisra.webquizengine.respositories

import dev.artisra.webquizengine.entities.ResolvedQuiz
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ResolvedQuizRepository : JpaRepository<ResolvedQuiz, Int> {
    fun findAllByUserId(userId: Int, pageable: Pageable): Page<ResolvedQuiz>
}