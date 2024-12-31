package dev.artisra.webquizengine.entities

import jakarta.persistence.*

@Entity
@Table(name ="users")
class AppUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "email")
    val email: String = "",

    @Column(name = "password")
    val password: String = "",

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val quizzes: List<Quiz> = mutableListOf()
) {
    override fun toString(): String {
        return "AppUser(id=$id,email=$email)"
    }
}