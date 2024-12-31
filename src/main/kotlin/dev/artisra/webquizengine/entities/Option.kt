package dev.artisra.webquizengine.entities

import jakarta.persistence.*

@Entity
@Table(name = "options")
data class Option(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name =  "option")
    val option: String = "",

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    var quiz: Quiz? = null
)