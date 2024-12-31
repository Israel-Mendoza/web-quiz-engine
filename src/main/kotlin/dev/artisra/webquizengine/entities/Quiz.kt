package dev.artisra.webquizengine.entities

import jakarta.persistence.*

@Entity
@Table(name = "quizzes")
data class Quiz(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "title", nullable = false)
    val title: String = "",

    @Column(name = "text", nullable = false)
    val text: String = "",

    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "quiz",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        targetEntity = Option::class
    )
    var options: List<Option> = mutableListOf(),

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quiz", cascade = [CascadeType.ALL], orphanRemoval = true, targetEntity = Answer::class)
    var answer: List<Answer> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz", cascade = [CascadeType.ALL], orphanRemoval = true, targetEntity = ResolvedQuiz::class)
    var resolvedQuiz: List<ResolvedQuiz> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: AppUser? = null
)