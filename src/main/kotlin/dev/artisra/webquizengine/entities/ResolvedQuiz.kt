package dev.artisra.webquizengine.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "resolved_quizzes")
data class ResolvedQuiz(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name =  "completed_at")
    val completedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    var quiz: Quiz? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: AppUser? = null
)