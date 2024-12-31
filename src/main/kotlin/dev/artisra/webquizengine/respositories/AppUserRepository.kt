package dev.artisra.webquizengine.respositories

import dev.artisra.webquizengine.entities.AppUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : CrudRepository<AppUser, Int> {
    fun findByEmail(email: String): MutableList<AppUser>
}