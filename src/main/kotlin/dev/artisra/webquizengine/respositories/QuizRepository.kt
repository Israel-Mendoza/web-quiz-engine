package dev.artisra.webquizengine.respositories

import dev.artisra.webquizengine.entities.Quiz
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizRepository : JpaRepository<Quiz, Int>