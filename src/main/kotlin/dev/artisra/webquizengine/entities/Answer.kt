package dev.artisra.webquizengine.entities

import jakarta.persistence.*

@Entity
@Table(name = "answers")
data class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "answer")
    val answer: Int = 0,

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    var quiz: Quiz? = null
)